package controllers;

import model.Customer;
import service.CustomerController;

import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by ShimaK on 08-Apr-17.
 */
@Path("/customer")
public class CustomerService {
    private CustomerController customerController;

    public CustomerService() {
        customerController = new CustomerController();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject getCustomers() {
        JsonObjectBuilder objbuild = Json.createObjectBuilder();
        JsonObject customers = customerController.getCustomers();

        if (customers == null || customers.getJsonArray("body").isEmpty()) {
            objbuild.add("status", "fail");
        } else {
            objbuild.add("status", "success");
            objbuild.add("body", customers.getJsonArray("body"));
        }
        return objbuild.build();
    }

    @Path("{accountNum}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject getCustomer(@PathParam("accountNum") String accountNUm) {
        JsonObjectBuilder objbuild = Json.createObjectBuilder();
        JsonObject customer = customerController.getCustomer(accountNUm);
        if (customer != null || !customer.isEmpty()) {
            objbuild.add("status", "success");
        } else {
            objbuild.add("status", "fail");
            objbuild.add("body",customer);
        }
        return objbuild.build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonObject createCustomer(JsonObject emp_json) {
        //TODO response error type
        int result = customerController.createCustomer(emp_json.getString("name"), emp_json.getString("dob")
                , emp_json.getString("address"), emp_json.getString("mobile"), emp_json.getString("email")
                , emp_json.getString("accountType"), emp_json.getString("accountNumber"), emp_json.getString("sortCode")
                , emp_json.getString("balance"), emp_json.getString("card"));

        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (result == 0) {
            builder.add("status", "success");
        } else if (result == -1) {
            builder.add("status", "fail");
        } else {
            builder.add("status", "exist");
        }
        return builder.build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject updateCustomer(JsonObject emp_json) {
        //TODO receive password and validate then make changes
        //TODO response error type
        boolean result = customerController.updateCustomer(emp_json.getString("accountNum")
                , emp_json.getString("column"), emp_json.getString("value"));

        JsonObjectBuilder builder = Json.createObjectBuilder();
        if (result) {
            builder.add("response", "success");
        } else {
            builder.add("response", "fail");
        }

        return builder.build();
    }

    @Path("{accountNum}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject deleteCustomer(@PathParam("accountNum") String accountNum) {
        boolean result = customerController.deleteCustomer(accountNum);
        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (result) {
            builder.add("status", "success");
        } else {
            builder.add("status", "fail");
        }
        return builder.build();
    }
}
