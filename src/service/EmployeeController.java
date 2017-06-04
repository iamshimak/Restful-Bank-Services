package service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import jdk.nashorn.api.scripting.JSObject;
import model.Employee;

import javax.json.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by ShimaK on 07-Apr-17.
 */
public class EmployeeController {
    public JsonObject getEmployees() {
        Connection conn;
        Statement statement;
        ResultSet result;

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "SELECT * FROM employee";

            result = statement.executeQuery(SQL);

            while (result.next()) {
                objectBuilder.add("name",result.getString("name"));
                objectBuilder.add("position",result.getString("position"));
                objectBuilder.add("username",result.getString("username"));
                objectBuilder.add("password",result.getString("password"));
                arrayBuilder.add(objectBuilder);
            }
            return objectBuilder.add("body", arrayBuilder.build()).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonObject getEmployee(String username) {
        Connection conn;
        Statement statement;
        ResultSet result;

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "SELECT * FROM employee WHERE username = '" + username + "'";

            result = statement.executeQuery(SQL);

            while (result.next()) {
                objectBuilder.add("name",result.getString("name"));
                objectBuilder.add("position",result.getString("position"));
                objectBuilder.add("username",result.getString("username"));
                objectBuilder.add("password",result.getString("password"));
            }
            return objectBuilder.build();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int createEmployee(String name, String position, String username, String password) {
        Connection conn = null;
        Statement statement;
        int sessionisSucess = -1;

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "INSERT INTO employee VALUES ('" + name + "','" + position + "','"
                    + username + "','" + password + "')";

            statement.executeUpdate(SQL);
            sessionisSucess = 0;

        } catch (MySQLIntegrityConstraintViolationException e) {
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sessionisSucess;
    }

    public boolean updateEmployee(String username, String column, String updateVal) {
        Connection conn = null;
        Statement statement;
        boolean sessionisSucess = false;

        try {
            conn = connectDB();
            statement = conn.createStatement();

            column = column.toLowerCase();
            if (!(column.equals("position") || column.equals("name") || column.equals("username")
                    || column.equals("password"))) {
                return false;
            }

            String SQL = "UPDATE employee SET " + column + " = '" + updateVal + "' WHERE username = '" + username + "'";
            statement.executeUpdate(SQL);
            sessionisSucess = true;

        } catch (MySQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sessionisSucess;
    }

    public boolean deleteEmployee(String username) {
        Connection conn = null;
        Statement statement;
        boolean sessionisSucess = false;

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "DELETE FROM employee WHERE username = '" + username + "'";
            statement.executeUpdate(SQL);
            sessionisSucess = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sessionisSucess;
    }

    public boolean validateLogin(String username, String password) {
        Connection conn = null;
        Statement statement;
        ResultSet result;
        boolean sessionisSucess = false;

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "SELECT password FROM employee WHERE username = '" + username + "'";
            result = statement.executeQuery(SQL);

            while (result.next()) {
                if (result.getString("password").equals(password)) {
                    sessionisSucess = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sessionisSucess;
    }

    private Connection connectDB() throws SQLException {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/bankdb", "root", "");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    //Test cases
    public static void main(String[] args) {
        EmployeeController employeeService = new EmployeeController();

        /*ArrayList<Employee> employes = employeeService.getEmployees();
        for (Employee emp : employes) {
            System.out.println(emp);
        }

        Employee employee = employeeService.getEmployee("BK");
        System.out.println(employee);*/

        //employeeService.createCustomer("naba", "CTO", "nababa", "no");

        //employeeService.updateCustomer("BK", "positions", "CEO");

        //employeeService.deleteCustomer("BKK");

        System.out.println(employeeService.validateLogin("iron","mana"));
    }
}
