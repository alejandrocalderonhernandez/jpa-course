package org.example;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final var jdbcUrl = "jdbc:h2:~/test;DB_CLOSE_DELAY=-1";
        final var userDB = "sa";
        final var password = "";
        final  var scriptPath = "src/main/resources/schema.sql";
        final var initDB = "RUNSCRIPT FROM '" + scriptPath + "'";
        final var query =  "SELECT " +
                "e.id, e.name, e.email, e.department_id, d.name AS department_name "
                + "FROM employees e "
                + "JOIN departments d ON e.department_id = d.id";


        try (var connection = DriverManager.getConnection(jdbcUrl, userDB, password)){
            var statement = connection.createStatement();
            statement.execute(initDB);

            var rs = statement.executeQuery(query);
            var employees = new ArrayList<EmployeeDTO>();

            while (rs.next()) {
                var employee = new EmployeeDTO();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setDepartmentId(rs.getInt("department_id"));
                employee.setDepartmentName(rs.getString("department_name"));

                employees.add(employee);
            }

            employees.forEach(System.out::println);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}