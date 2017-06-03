package service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import model.Customer;

import javax.json.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by ShimaK on 08-Apr-17.
 */
public class CustomerController {
    public JsonObject getCustomers() {
        Connection conn;
        Statement statement;
        ResultSet result;

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "SELECT * FROM customer";

            result = statement.executeQuery(SQL);

            while (result.next()) {
                objectBuilder.add("name", result.getString("name"));
                objectBuilder.add("dob", result.getDate("dob").toString());
                objectBuilder.add("address", result.getString("address"));
                objectBuilder.add("mobile", result.getString("mobile"));
                objectBuilder.add("email", result.getString("email"));
                objectBuilder.add("accountType", result.getString("accountType"));
                objectBuilder.add("accountNumber", result.getString("accountNumber"));
                objectBuilder.add("sortCode", result.getString("sortCode"));
                objectBuilder.add("balance", result.getString("balance"));
                objectBuilder.add("card", result.getString("card"));
                arrayBuilder.add(objectBuilder);
            }
            return objectBuilder.add("body", arrayBuilder.build()).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonObject getCustomer(String accountNum) {
        Connection conn;
        Statement statement;
        ResultSet result;
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "SELECT * FROM customer WHERE accountNumber = '" + accountNum + "'";

            result = statement.executeQuery(SQL);

            result.next();

            objectBuilder.add("name", result.getString("name"));
            objectBuilder.add("dob", result.getDate("dob").toString());
            objectBuilder.add("address", result.getString("address"));
            objectBuilder.add("mobile", result.getString("mobile"));
            objectBuilder.add("email", result.getString("email"));
            objectBuilder.add("accountType", result.getString("accountType"));
            objectBuilder.add("accountNumber", result.getString("accountNumber"));
            objectBuilder.add("sortCode", result.getString("sortCode"));
            objectBuilder.add("balance", result.getString("balance"));
            objectBuilder.add("card", result.getString("card"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objectBuilder.build();
    }

    public int createCustomer(String name, String dob, String address, String mobile, String email, String accountType,
                              String accountNum, String sortCode, String balance, String card) {

        String[] date = dob.split(":");
        Date dateformated = new Date(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[1]));
        Timestamp stamp = new Timestamp(dateformated.getTime());

        for (String s : date) {
            System.out.println(s);
        }

        Connection conn = null;
        Statement statement;
        int sessionisSucess = -1;

        try {
            conn = connectDB();
            statement = conn.createStatement();

            String SQL = "INSERT INTO customer VALUES ('" + name + "','" + stamp.toString() + "','" + address + "','"
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
                assert conn != null;
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
            int session = statement.executeUpdate(SQL);
            if (session > 0) sessionisSucess = true;

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
        CustomerController customerService = new CustomerController();
        //customerService.getCustomers();

        /*customerService.createCustomer("shimak","1998:12:21","colombo","0752199219","shimak2013@gmail.com","VIP",
        "1","55646","3000","498488");*/

        //System.out.println(customerService.deleteCustomer("1"));
    }
}
