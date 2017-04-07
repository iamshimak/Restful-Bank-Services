package service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by ShimaK on 07-Apr-17.
 */
public class EmployeeService {
    public ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        Connection conn;
        Statement statement;
        ResultSet result;

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "SELECT * FROM employee";

            result = statement.executeQuery(SQL);

            while (result.next()) {
                employees.add(new Employee(result.getString("name"), result.getString("position")
                        , result.getString("username"), result.getString("password")));
            }
            return employees;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee getEmployee(String username) {
        Connection conn;
        Statement statement;
        ResultSet result;
        Employee employees = null;

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "SELECT * FROM employee WHERE username = '" + username + "'";

            result = statement.executeQuery(SQL);

            while (result.next()) {
                employees = new Employee(result.getString("name"), result.getString("position")
                        , result.getString("username"), result.getString("password"));
            }
            return employees;

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
            return sessionisSucess;
        }
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
            return sessionisSucess;
        }
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
            return sessionisSucess;
        }
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
        EmployeeService employeeService = new EmployeeService();

        ArrayList<Employee> employes = employeeService.getEmployees();
        for (Employee emp : employes) {
            System.out.println(emp);
        }

        Employee employee = employeeService.getEmployee("BK");
        System.out.println(employee);

        //employeeService.createEmployee("naba", "CTO", "nababa", "no");

        //employeeService.updateEmployee("BK", "positions", "CEO");

        employeeService.deleteEmployee("BKK");
    }
}
