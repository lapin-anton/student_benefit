package ru.java_project.student_benefit.dao;

import ru.java_project.student_benefit.config.Config;
import ru.java_project.student_benefit.domain.address.Street;
import ru.java_project.student_benefit.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDAOImpl implements DictionaryDAO {

    private static final String GET_STREET = "SELECT * FROM jc_street WHERE UPPER(street_name) LIKE UPPER(?)";

    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN), Config.getProperty(Config.DB_PASSWORD));
        return connection;
    }

    public List<Street> findStreets(String input) throws DaoException {
        List<Street> result = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_STREET)) {
            stmt.setString(1, "%" + input + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Street(rs.getLong(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

}
