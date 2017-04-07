package controllers;

import model.Employee;
import service.EmployeeService;

import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by ShimaK on 07-Apr-17.
 */
@Path("/employee")
public class bank_employee {
    private EmployeeService employeeService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject getEmployees() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonObjectBuilder objbuild = Json.createObjectBuilder();

        ArrayList<Employee> employees = new EmployeeService().getEmployees();

        if (employees == null || employees.size() < 1) {
            objbuild.add("status", "fail");
        } else {
            for (Employee employee : employees) {
                objbuild.add("name", employee.getName());
                objbuild.add("position", employee.getPosition());
                objbuild.add("username", employee.getUsername());
                objbuild.add("password", employee.getPassword());
                builder.add(objbuild);
            }
            objbuild.add("status", "success");
        }

        return objbuild.add("body", builder.build()).build();
    }

    @Path("{username}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject getEmployee(@PathParam("username") String username) {
        JsonObjectBuilder objbuild = Json.createObjectBuilder();

        Employee employee = new EmployeeService().getEmployee(username);
        if (employee != null) {
            objbuild.add("status", "success");
            objbuild.add("name", employee.getName());
            objbuild.add("position", employee.getPosition());
            objbuild.add("username", employee.getUsername());
            objbuild.add("password", employee.getPassword());
        } else {
            objbuild.add("status", "fail");
        }

        return objbuild.build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonObject createEmployee(JsonObject emp_json) {
        //TODO response error type
        int result = new EmployeeService().createEmployee(emp_json.getString("name"), emp_json.getString("position")
                , emp_json.getString("username"), emp_json.getString("password"));

        JsonObjectBuilder builder = Json.createObjectBuilder();
        if (result == 0) {
            builder.add("response", "success");
        } else if (result == -1) {
            builder.add("response", "fail");
        } else {
            builder.add("response", "exist");
        }

        return builder.build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonObject updateEmployee(JsonObject emp_json) {
        //TODO receive password and validate then make changes
        //TODO response error type
        boolean result = new EmployeeService().updateEmployee(emp_json.getString("username")
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
        boolean result = new EmployeeService().deleteEmployee(username);

        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (result) {
            builder.add("response", "success");
        } else {
            builder.add("response", "fail");
        }

        return builder.build();
    }
}
