package ru.java_project.student_benefit.dao;

import ru.java_project.student_benefit.domain.address.Street;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDAO {

    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/jc_student?useUnicode=true&serverTimezone=UTC",
                "root", "1111");
        return connection;
    }

    public List<Street> findStreets(String input) throws Exception {
        List<Street> result = new ArrayList<>();
        Connection connection = getConnection();
        String sql = String.format("SELECT * FROM jc_street WHERE UPPER(street_name) LIKE UPPER(\"%%%s%%\")", input);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            result.add(new Street(rs.getLong(1), rs.getString(2)));
        }
        return result;
    }

}
