package ru.java_project.student_benefit.dao;

import ru.java_project.student_benefit.config.Config;
import ru.java_project.student_benefit.domain.CountryArea;
import ru.java_project.student_benefit.domain.PassportOffice;
import ru.java_project.student_benefit.domain.RegisterOffice;
import ru.java_project.student_benefit.domain.address.Street;
import ru.java_project.student_benefit.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDAOImpl implements DictionaryDAO {

    private static final String GET_STREET = "SELECT * FROM jc_street WHERE UPPER(street_name) LIKE UPPER(?)";
    private static final String GET_PASSPORT_OFFICE = "SELECT * FROM jc_passport_office WHERE office_area_id=?";
    private static final String GET_REGISTER_OFFICE = "SELECT * FROM jc_register_office WHERE r_area_id=?";
    private static final String GET_AREA = "SELECT * FROM jc_country_struct WHERE area_id like ? " +
            "and area_id <> ?";

    //TODO make one method
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

    @Override
    public List<PassportOffice> findPassportOffices(String areaId) throws DaoException {
        List<PassportOffice> result = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_PASSPORT_OFFICE)) {
            stmt.setString(1, areaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new PassportOffice(rs.getLong("office_id"),
                        rs.getString("office_area_id"), rs.getString("office_name")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException {
        List<RegisterOffice> result = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_REGISTER_OFFICE)) {
            stmt.setString(1, areaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new RegisterOffice(rs.getLong("r_office_id"),
                        rs.getString("r_area_id"), rs.getString("r_office_name")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<CountryArea> findArea(String areaId) throws Exception {
        List<CountryArea> result = new ArrayList<>();
        String param1 = buildParam(areaId);
        String param2 = areaId;
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(GET_AREA)) {
            stmt.setString(1, param1);
            stmt.setString(2, param2);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new CountryArea(rs.getString("area_id"),
                        rs.getString("area_name")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    private String buildParam(String areaId) throws SQLException{
        if(areaId == null || areaId.trim().equals("")) {
            return "__0000000000";
        } else if (areaId.endsWith("0000000000")) {
            return areaId.substring(0, 2) + "___0000000";
        } else if (areaId.endsWith("0000000")) {
            return areaId.substring(0, 5) + "___0000";
        } else if (areaId.endsWith("0000")) {
            return areaId.substring(0, 8) + "____";
        }
        throw new SQLException("invalid parameter areaId" + areaId);
    }
}
