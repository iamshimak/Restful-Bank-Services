package controllers;

import service.EmployeeController;

import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by ShimaK on 07-Apr-17.
 */
@Path("/employee")
public class EmployeeService {
    private EmployeeController employeeController;

    public EmployeeService() {
        employeeController = new EmployeeController();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject getEmployees() {
        JsonObjectBuilder objbuild = Json.createObjectBuilder();
        JsonObject employees = employeeController.getEmployees();

        if (employees == null || employees.isEmpty()) {
            objbuild.add("status", "fail");
        } else {
            objbuild.add("status", "success");
            objbuild.add("body", employees.getJsonArray("body"));
        }

        return objbuild.build();
    }

    @Path("{username}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject getEmployee(@PathParam("username") String username) {
        JsonObjectBuilder objbuild = Json.createObjectBuilder();
        JsonObject employee = employeeController.getEmployee(username);
        if (employee != null || !employee.isEmpty()) {
            objbuild.add("status", "success");
        } else {
            objbuild.add("status", "fail");
            objbuild.add("body", employee);
        }
        return objbuild.build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonObject createEmployee(JsonObject emp_json) {
        //TODO response error type
        int result = employeeController.createEmployee(emp_json.getString("name"), emp_json.getString("position")
                , emp_json.getString("username"), emp_json.getString("password"));

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
    public JsonObject updateEmployee(JsonObject emp_json) {
        //TODO receive password and validate then make changes
        //TODO response error type
        boolean result = employeeController.updateEmployee(emp_json.getString("username")
                , emp_json.getString("column"), emp_json.getString("value"));

        JsonObjectBuilder builder = Json.createObjectBuilder();
        if (result) {
            builder.add("response", "success");
        } else {
            builder.add("response", "fail");
        }

        return builder.build();
    }

    @Path("{username}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject deleteEmployee(@PathParam("username") String username) {
        boolean result = employeeController.deleteEmployee(username);

        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (result) {
            builder.add("response", "success");
        } else {
            builder.add("response", "fail");
        }

        return builder.build();
    }

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject validateLogin(JsonObject json) {
        //TODO change this to FormParams
        boolean result = employeeController.validateLogin(json.getString("username"), json.getString("password"));

        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (result) {
            builder.add("response", "success");
        } else {
            builder.add("response", "fail");
        }

        return builder.build();
    }
}
