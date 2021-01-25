package ru.java_project.student_benefit.dao;

import ru.java_project.student_benefit.config.Config;
import ru.java_project.student_benefit.domain.StudentOrder;
import ru.java_project.student_benefit.domain.StudentOrderStatus;
import ru.java_project.student_benefit.exception.DaoException;

import java.sql.*;

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

    //TODO make one method
    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN), Config.getProperty(Config.DB_PASSWORD));
        return connection;
    }

    @Override
    public Long saveStudentOrder(StudentOrder order) throws DaoException {
        Long result = 0L;
        try (Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(INSERT_ORDER)) {
            stmt.setString(1, order.getHusband().getSurName());
            stmt.setString(2, order.getHusband().getGivenName());
            stmt.setString(3, order.getHusband().getPatronymic());
            stmt.setDate(4, Date.valueOf(order.getHusband().getDateOfBirth()));
            stmt.setString(5, order.getHusband().getAddress().getPostCode());
            stmt.setLong(6, order.getHusband().getAddress().getStreet().getStreetCode());
            stmt.setString(7, order.getHusband().getAddress().getBuilding());
            stmt.setString(8, order.getHusband().getAddress().getExtension());
            stmt.setString(9, order.getHusband().getAddress().getApartment());
            stmt.setString(10, order.getWife().getSurName());
            stmt.setString(11, order.getWife().getGivenName());
            stmt.setString(12, order.getWife().getPatronymic());
            stmt.setDate(13, Date.valueOf(order.getWife().getDateOfBirth()));
            stmt.setString(14, order.getWife().getAddress().getPostCode());
            stmt.setLong(15, order.getWife().getAddress().getStreet().getStreetCode());
            stmt.setString(16, order.getWife().getAddress().getBuilding());
            stmt.setString(17, order.getWife().getAddress().getExtension());
            stmt.setString(18, order.getWife().getAddress().getApartment());
            stmt.setString(19, order.getMarriageCertificateId());
            stmt.setLong(20, order.getRegisterOffice().getOfficeId());
            stmt.setDate(21, Date.valueOf(order.getMarriageDate()));
            stmt.setString(22, order.getHusband().getPassportSer());
            stmt.setString(23, order.getHusband().getPassportNumber());
            stmt.setDate(24, Date.valueOf(order.getHusband().getIssueDate()));
            stmt.setLong(25, order.getHusband().getPassportOffice().getOfficeId());
            stmt.setString(26, order.getWife().getPassportSer());
            stmt.setString(27, order.getHusband().getPassportNumber());
            stmt.setDate(28, Date.valueOf(order.getHusband().getIssueDate()));
            stmt.setLong(29, order.getHusband().getPassportOffice().getOfficeId());
            stmt.setInt(30, StudentOrderStatus.START.ordinal());
            stmt.setDate(31, Date.valueOf(order.getStudentOrderDate()));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }
}
