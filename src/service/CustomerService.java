package service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import model.Card;
import model.Customer;

import java.sql.*;
import java.util.*;

/**
 * Created by ShimaK on 08-Apr-17.
 */
public class CustomerService {
    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        Connection conn;
        Statement statement;
        ResultSet result;

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "SELECT * FROM customer";

            result = statement.executeQuery(SQL);

            while (result.next()) {
                customers.add(new Customer(result.getString("name"), result.getDate("dob"), result.getString("address")
                        , result.getString("mobile"), result.getString("email"), result.getString("accountType")
                        , result.getString("accountNumber"), result.getString("sortCode")
                        , Integer.parseInt(result.getString("balance"))
                        , result.getString("card")));
                System.out.println(customers.toString());
            }
            return customers;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer getCustomer(String accountNum) {
        Customer customer = null;
        Connection conn;
        Statement statement;
        ResultSet result;

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "SELECT * FROM customer WHERE accountNumber = '" + accountNum + "'";

            result = statement.executeQuery(SQL);

            while (result.next()) {
                customer = new Customer(result.getString("name"), result.getDate("dob"), result.getString("address")
                        , result.getString("mobile"), result.getString("email"), result.getString("accountType")
                        , result.getString("accountNumber"), result.getString("sortCode")
                        , Integer.parseInt(result.getString("balance"))
                        , result.getString("card"));
                System.out.println(customer.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    public int createCustomer(String name, String dob, String address, String mobile, String email, String accountType,
                              String accountNum, String sortCode, String balance, String card) {
        Connection conn = null;
        Statement statement;
        int sessionisSucess = -1;

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "INSERT INTO customer VALUES ('" + name + "','" + dob + "','" + address + "','"
                    + mobile + "','" + email + "','" + accountType + "','" + accountNum + "','" + sortCode + "','"
                    + balance + "','" + card + "')";

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

    public boolean updateCustomer(String accountNum, String column, String updateVal) {
        Connection conn = null;
        Statement statement;
        boolean sessionisSucess = false;

        try {
            conn = connectDB();
            statement = conn.createStatement();

            column = column.toLowerCase();
            if (!(column.equals("name") || column.equals("dob") || column.equals("address")
                    || column.equals("mobile")) || column.equals("email") || column.equals("accountType")
                    || column.equals("accountNumber") || column.equals("sortCode") || column.equals("balance")
                    || column.equals("card")) {
                return false;
            }

            //TODO create a new method for add cards

            String SQL = "UPDATE customer SET " + column + " = '" + updateVal
                    + "' WHERE accountNumber = '" + accountNum + "'";
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

    public boolean deleteCustomer(String accountNum) {
        Connection conn = null;
        Statement statement;
        boolean sessionisSucess = false;

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "DELETE FROM customer WHERE accountNumber = '" + accountNum + "'";
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

    private Connection connectDB() throws SQLException {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/bankdb", "root", "");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        customerService.getCustomers();
    }
}
