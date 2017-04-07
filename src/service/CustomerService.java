package service;

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

                ArrayList<Card> cards = new ArrayList<>();
                cards.add(new Card("commercial", result.getString("card")));

                customers.add(new Customer(result.getString("name"), result.getDate("dob"), result.getString("address")
                        , result.getString("mobile"), result.getString("email"), result.getString("accountType")
                        , result.getString("accountNumber"), result.getString("sortCode")
                        , Integer.parseInt(result.getString("balance"))
                        , cards));
                System.out.println(customers.toString());
            }
            return customers;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
