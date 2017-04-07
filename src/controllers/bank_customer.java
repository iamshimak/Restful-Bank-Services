package controllers;

import model.Customer;
import model.Employee;
import service.CustomerService;
import service.EmployeeService;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by ShimaK on 08-Apr-17.
 */
@Path("/customer")
public class bank_customer {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject getCustomers() {
        ArrayList<Customer> customers = new CustomerService().getCustomers();

        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonObjectBuilder objbuild = Json.createObjectBuilder();

        if (customers == null || customers.size() < 1) {
            objbuild.add("status", "fail");
        } else {
            for (Customer employee : customers) {
                objbuild.add("name", employee.getName());
                objbuild.add("dob", employee.getDob().toString());
                objbuild.add("address", employee.getAddress());
                objbuild.add("mobile", employee.getMobile());
                builder.add(objbuild);
            }
            objbuild.add("status", "success");
        }

        return objbuild.add("body", builder.build()).build();
    }
}
