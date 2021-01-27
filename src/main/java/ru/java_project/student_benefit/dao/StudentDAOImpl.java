package ru.java_project.student_benefit.dao;

import ru.java_project.student_benefit.config.Config;
import ru.java_project.student_benefit.domain.StudentOrder;
import ru.java_project.student_benefit.domain.StudentOrderStatus;
import ru.java_project.student_benefit.domain.address.Address;
import ru.java_project.student_benefit.domain.person.*;
import ru.java_project.student_benefit.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;

public class StudentDAOImpl implements StudentOrderDAO {

    private static final String INSERT_ORDER =
            "INSERT INTO `jc_student_order`" +
                    "(`h_sur_name`," +
                    "`h_given_name`," +
                    "`h_patronymic`," +
                    "`h_birth_date`," +
                    "`h_post_index`," +
                    "`h_street_code`," +
                    "`h_building`," +
                    "`h_extension`," +
                    "`h_apartment`," +
                    "`w_sur_name`," +
                    "`w_given_name`," +
                    "`w_patrnymic`," +
                    "`w_birth_date`," +
                    "`w_post_index`," +
                    "`w_street_code`," +
                    "`w_building`," +
                    "`w_extension`," +
                    "`w_apartment`," +
                    "`certificate_id`," +
                    "`register_office_id`," +
                    "`marriage_date`," +
                    "`h_passport_seria`," +
                    "`h_passport_number`," +
                    "`h_passport_date`," +
                    "`h_passport_office_id`," +
                    "`w_passport_seria`," +
                    "`w_passport_number`," +
                    "`w_passport_date`," +
                    "`w_passport_office_id`," +
                    "`student_order_status`," +
                    "`student_order_date`)" +
                    "VALUES" +
                    "(?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?,?)";

    private static final String INSERT_CHILD = "INSERT INTO `jc_student_child`\n" +
            "(`student_order_id`," +
            "`c_sur_name`," +
            "`c_given_name`," +
            "`c_patronymic`," +
            "`c_birth_date`," +
            "`c_certificate_number`," +
            "`c_registration_date`," +
            "`c_register_office_id`," +
            "`c_post_index`," +
            "`c_street_code`," +
            "`c_building`," +
            "`c_extension`," +
            "`c_apartment`)" +
            "VALUES" +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    //TODO make one method
    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN), Config.getProperty(Config.DB_PASSWORD));
        return connection;
    }

    @Override
    public Long saveStudentOrder(StudentOrder order) throws DaoException {
        Long result = -1L;
        try (Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(INSERT_ORDER, new String[]{"id"})) {
            Adult husband = order.getHusband();
            Adult wife = order.getWife();
            connection.setAutoCommit(false);
            try {
                setPersonParams(husband, stmt, 1);
                setPersonParams(wife, stmt, 10);

                stmt.setString(19, order.getMarriageCertificateId());
                stmt.setLong(20, order.getRegisterOffice().getOfficeId());
                stmt.setDate(21, Date.valueOf(order.getMarriageDate()));

                setPassportData(stmt, husband, 22);
                setPassportData(stmt, wife, 26);

                stmt.setInt(30, StudentOrderStatus.START.ordinal());
                stmt.setDate(31, Date.valueOf(LocalDate.now()));

                stmt.executeUpdate();

                ResultSet gkRs = stmt.getGeneratedKeys();

                if (gkRs.next()) {
                    result = gkRs.getLong(1);
                }
                gkRs.close();

                saveChildren(connection, order, result);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    private void saveChildren(Connection connection, StudentOrder order, Long orderId) throws SQLException {
        try(PreparedStatement stmt = connection.prepareStatement(INSERT_CHILD)) {
            for (Child child : order.getChildren()) {
                stmt.setLong(1, orderId);

                stmt.setString(2, child.getSurName());
                stmt.setString(3, child.getGivenName());
                stmt.setString(4, child.getPatronymic());
                stmt.setDate(5, Date.valueOf(child.getDateOfBirth()));

                stmt.setString(6, child.getCertificateNumber());
                stmt.setDate(7, Date.valueOf(child.getIssueDate()));
                stmt.setLong(8, child.getRegisterOffice().getOfficeId());
                Address address = child.getAddress();
                stmt.setString(9, address.getPostCode());
                stmt.setLong(10, address.getStreet().getStreetCode());
                stmt.setString(11, address.getBuilding());
                stmt.setString(12, address.getExtension());
                stmt.setString(13, address.getApartment());

                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {

        }
    }

    private void setPassportData(PreparedStatement stmt, Adult adult, int start) throws SQLException {
        stmt.setString(start, adult.getPassportSer());
        stmt.setString(start + 1, adult.getPassportNumber());
        stmt.setDate(start + 2, Date.valueOf(adult.getIssueDate()));
        stmt.setLong(start + 3, adult.getPassportOffice().getOfficeId());
    }

    private void setPersonParams(Person person, PreparedStatement stmt, int start) throws SQLException {
        stmt.setString(start, person.getSurName());
        stmt.setString(start + 1, person.getGivenName());
        stmt.setString(start + 2, person.getPatronymic());
        stmt.setDate(start + 3, Date.valueOf(person.getDateOfBirth()));
        stmt.setString(start + 4, person.getAddress().getPostCode());
        stmt.setLong(start + 5, person.getAddress().getStreet().getStreetCode());
        stmt.setString(start + 6, person.getAddress().getBuilding());
        stmt.setString(start + 7, person.getAddress().getExtension());
        stmt.setString(start + 8, person.getAddress().getApartment());
    }
}
