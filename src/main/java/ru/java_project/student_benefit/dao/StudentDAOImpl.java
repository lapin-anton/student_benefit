package ru.java_project.student_benefit.dao;

import ru.java_project.student_benefit.config.Config;
import ru.java_project.student_benefit.domain.*;
import ru.java_project.student_benefit.domain.address.Address;
import ru.java_project.student_benefit.domain.address.Street;
import ru.java_project.student_benefit.domain.person.Adult;
import ru.java_project.student_benefit.domain.person.Child;
import ru.java_project.student_benefit.domain.person.Person;
import ru.java_project.student_benefit.exception.DaoException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDAOImpl implements StudentOrderDAO {

    private static final String INSERT_ORDER =
            "INSERT INTO jc_student_order(" +
                    " student_order_status, student_order_date, h_sur_name, " +
                    " h_given_name, h_patronymic, h_date_of_birth, h_passport_seria, " +
                    " h_passport_number, h_passport_date, h_passport_office_id, h_post_index, " +
                    " h_street_code, h_building, h_extension, h_apartment, h_university_id, h_student_number, " +
                    " w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_seria, " +
                    " w_passport_number, w_passport_date, w_passport_office_id, w_post_index, " +
                    " w_street_code, w_building, w_extension, w_apartment, w_university_id, w_student_number, " +
                    " certificate_id, register_office_id, marriage_date)" +
                    " VALUES (?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, ?, " +
                    " ?, ?, ?);";

    private static final String INSERT_CHILD =
            "INSERT INTO jc_student_child(" +
                    " student_order_id, c_sur_name, c_given_name, " +
                    " c_patronymic, c_date_of_birth, c_certificate_number, c_certificate_date, " +
                    " c_register_office_id, c_post_index, c_street_code, c_building, " +
                    " c_extension, c_apartment)" +
                    " VALUES (?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?)";

    private static final String SELECT_ORDERS =
                    "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
                    "h_po.p_office_area_id as h_p_office_area_id, h_po.p_office_name as h_p_office_name, " +
                    "w_po.p_office_area_id as w_p_office_area_id, w_po.p_office_name as w_p_office_name " +
                    "FROM jc_student_order so " +
                    "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
                    "INNER JOIN jc_passport_office h_po ON h_po.p_office_id = so.h_passport_office_id " +
                    "INNER JOIN jc_passport_office w_po ON w_po.p_office_id = so.w_passport_office_id" +
                    " WHERE student_order_status = ? ORDER BY student_order_date LIMIT ?";

    private static final String SELECT_CHILD =
            "SELECT soc.*, ro.r_office_area_id as r_office_area_id, ro.r_office_name as r_office_name " +
            "FROM jc_student_child soc " +
            "INNER JOIN jc_register_office ro " +
            "ON ro.r_office_id = soc.c_register_office_id " +
            "WHERE soc.student_order_id IN";

    private static final String SELECT_ORDERS_FULL =
            "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
                    "h_po.p_office_area_id as h_p_office_area_id, h_po.p_office_name as h_p_office_name, " +
                    "w_po.p_office_area_id as w_p_office_area_id, w_po.p_office_name as w_p_office_name, " +
                    "soc.*, ro_c.r_office_area_id as r_office_area_id, ro_c.r_office_name as r_office_name " +
                    "FROM jc_student_order so " +
                    "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
                    "INNER JOIN jc_passport_office h_po ON h_po.p_office_id = so.h_passport_office_id " +
                    "INNER JOIN jc_passport_office w_po ON w_po.p_office_id = so.w_passport_office_id " +
                    "INNER JOIN jc_student_child soc ON soc.student_order_id = so.student_order_id " +
                    "INNER JOIN jc_register_office ro_c ON ro_c.r_office_id = soc.c_register_office_id " +
                    " WHERE student_order_status = ? ORDER BY so.student_order_id LIMIT ?";

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

    @Override
    public Long saveStudentOrder(StudentOrder so) throws DaoException {
        Long result = -1L;

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) {

            con.setAutoCommit(false);
            try {
                // Header
                stmt.setInt(1, StudentOrderStatus.START.ordinal());
                stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

                // Husband and wife
                setParamsForAdult(stmt, 3, so.getHusband());
                setParamsForAdult(stmt, 18, so.getWife());

                // Marriage
                stmt.setString(33, so.getMarriageCertificateId());
                stmt.setLong(34, so.getRegisterOffice().getOfficeId());
                stmt.setDate(35, java.sql.Date.valueOf(so.getMarriageDate()));

                stmt.executeUpdate();

                ResultSet gkRs = stmt.getGeneratedKeys();
                if (gkRs.next()) {
                    result = gkRs.getLong(1);
                }
                gkRs.close();

                saveChildren(con, so, result);

                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            }

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

        return result;
    }

    private void saveChildren(Connection con, StudentOrder so, Long soId) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(INSERT_CHILD)) {
            for (Child child : so.getChildren()) {
                stmt.setLong(1, soId);
                setParamsForChild(stmt, child);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private void setParamsForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
        setParamsForPerson(stmt, start, adult);
        stmt.setString(start + 4, adult.getPassportSer());
        stmt.setString(start + 5, adult.getPassportNumber());
        stmt.setDate(start + 6, java.sql.Date.valueOf(adult.getIssueDate()));
        stmt.setLong(start + 7, adult.getPassportOffice().getOfficeId());
        setParamsForAddress(stmt, start + 8, adult);
        stmt.setLong(start + 13, adult.getUniversity().getUniversityId());
        stmt.setString(start + 14, adult.getStudentId());
    }

    private void setParamsForChild(PreparedStatement stmt, Child child) throws SQLException {
        setParamsForPerson(stmt, 2, child);
        stmt.setString(6, child.getCertificateNumber());
        stmt.setDate(7, java.sql.Date.valueOf(child.getIssueDate()));
        stmt.setLong(8, child.getRegisterOffice().getOfficeId());
        setParamsForAddress(stmt, 9, child);
    }

    private void setParamsForPerson(PreparedStatement stmt, int start, Person person) throws SQLException {
        stmt.setString(start, person.getSurName());
        stmt.setString(start + 1, person.getGivenName());
        stmt.setString(start + 2, person.getPatronymic());
        stmt.setDate(start + 3, java.sql.Date.valueOf(person.getDateOfBirth()));
    }

    private void setParamsForAddress(PreparedStatement stmt, int start, Person person) throws SQLException {
        Address adult_address = person.getAddress();
        stmt.setString(start, adult_address.getPostCode());
        stmt.setLong(start + 1, adult_address.getStreet().getStreetCode());
        stmt.setString(start + 2, adult_address.getBuilding());
        stmt.setString(start + 3, adult_address.getExtension());
        stmt.setString(start + 4, adult_address.getApartment());
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DaoException {
        return getStudentOrdersOneSelect();
        //return getStudentOrdersTwoSelect();
    }

    private List<StudentOrder> getStudentOrdersOneSelect() throws DaoException {
        List<StudentOrder> result = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(SELECT_ORDERS_FULL)) {
            Map<Long, StudentOrder> map = new HashMap<>();
            stmt.setInt(1, StudentOrderStatus.START.ordinal());
            int limit = Integer.parseInt(Config.getProperty(Config.DB_LIMIT));
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                Long soId = rs.getLong("student_order_id");
                if(!map.containsKey(soId)) {
                    StudentOrder order = getFullStudentOrder(rs);
                    result.add(order);
                    map.put(soId, order);
                    count++;
                }
                if(count >= limit) {
                    result.remove(result.size() - 1);
                }
                StudentOrder so = map.get(soId);
                Child ch = fillChild(rs);
                so.addChild(ch);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    private List<StudentOrder> getStudentOrdersTwoSelect() throws DaoException {
        List<StudentOrder> result = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(SELECT_ORDERS)) {
            stmt.setInt(1, StudentOrderStatus.START.ordinal());
            stmt.setInt(2, Integer.parseInt(Config.getProperty(Config.DB_LIMIT)));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                StudentOrder order = getFullStudentOrder(rs);
                result.add(order);
            }
            findChildren(connection, result);
            rs.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    private StudentOrder getFullStudentOrder(ResultSet rs) throws SQLException {
        StudentOrder order = new StudentOrder();
        fillStudentOrder(rs, order);
        fillWedding(rs, order);
        order.setHusband(fillAdult(rs, "h_"));
        order.setWife(fillAdult(rs, "w_"));
        return order;
    }

    private void findChildren(Connection connection, List<StudentOrder> result) throws SQLException {
        String arg = "(" + result.stream().map(so -> String.valueOf(so.getStudentOrderId()))
                .collect(Collectors.joining(",")) + ")";

        Map<Long, StudentOrder> map = result.stream()
                .collect(Collectors.toMap(so -> so.getStudentOrderId(), so -> so));

        try(PreparedStatement stmt = connection.prepareStatement(SELECT_CHILD + arg)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Child ch = fillChild(rs);
                StudentOrder so = map.get(rs.getLong("student_order_id"));
                so.addChild(ch);
            }
        }
    }

    private Child fillChild(ResultSet rs) throws SQLException {
        Child result = new Child();
        result.setSurName(rs.getString("c_sur_name"));
        result.setGivenName(rs.getString("c_given_name"));
        result.setPatronymic(rs.getString("c_patronymic"));
        result.setDateOfBirth(rs.getDate("c_date_of_birth").toLocalDate());
        result.setAddress(fillAddress(rs, "c_"));
        result.setCertificateNumber(rs.getString("c_certificate_number"));
        result.setIssueDate(rs.getDate("c_certificate_date").toLocalDate());
        Long roId = rs.getLong("c_register_office_id");
        String roAreaId = rs.getString("r_office_area_id");
        String roName = rs.getString("r_office_name");
        RegisterOffice ro = new RegisterOffice(roId, roAreaId, roName);
        result.setRegisterOffice(ro);
        return result;
    }

    private Adult fillAdult(ResultSet rs, String prefix) throws SQLException {
        Adult adult = new Adult();
        adult.setSurName(rs.getString(prefix + "sur_name"));
        adult.setGivenName(rs.getString(prefix + "given_name"));
        adult.setPatronymic(rs.getString(prefix + "patronymic"));
        adult.setDateOfBirth(rs.getDate(prefix + "date_of_birth").toLocalDate());
        adult.setPassportSer(rs.getString(prefix + "passport_seria"));
        adult.setPassportNumber(rs.getString(prefix + "passport_number"));
        adult.setIssueDate(rs.getDate(prefix + "passport_date").toLocalDate());
        Long poId = rs.getLong(prefix + "passport_office_id");
        String poAreaId = rs.getString(prefix + "p_office_area_id");
        String poName = rs.getString(prefix + "p_office_name");
        PassportOffice po = new PassportOffice(poId, poAreaId, poName);
        adult.setPassportOffice(po);
        Address address = fillAddress(rs, prefix);
        adult.setAddress(address);
        Long universityId = rs.getLong(prefix + "university_id");
        University university = new University(universityId, "");
        adult.setUniversity(university);
        adult.setStudentId(rs.getString(prefix + "student_number"));
        return adult;
    }

    private Address fillAddress(ResultSet rs, String prefix) throws SQLException {
        Address address = new Address();
        address.setPostCode(rs.getString(prefix + "post_index"));
        Long streetCode = rs.getLong(prefix + "street_code");
        Street street = new Street(streetCode, "");
        address.setStreet(street);
        address.setBuilding(rs.getString(prefix + "building"));
        address.setExtension(rs.getString(prefix + "extension"));
        address.setApartment(rs.getString(prefix + "apartment"));
        return address;
    }

    private void fillWedding(ResultSet rs, StudentOrder order) throws SQLException {
        order.setMarriageCertificateId(rs.getString("certificate_id"));
        Long roId = rs.getLong("register_office_id");
        String roAreaId = rs.getString("r_office_area_id");
        String roOfficeName = rs.getString("r_office_name");
        RegisterOffice ro = new RegisterOffice(roId, roAreaId, roOfficeName);
        order.setRegisterOffice(ro);
        order.setMarriageDate(rs.getDate("marriage_date").toLocalDate());
    }

    private void fillStudentOrder(ResultSet rs, StudentOrder order) throws SQLException{
        order.setStudentOrderId(rs.getLong("student_order_id"));
        order.setStudentOrderStatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));
        order.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
    }
}
