package org.cxfexamples.cxf.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Department")
public class Department {
    private int id;
    private String name;
    private List<Employee> employees = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @GET
    @Path("{employeeId}")
    public Employee getEmployee(@PathParam("employeeId") int employeeId) {
        return findById(employeeId);
    }

    @POST
    public Response createEmployee(Employee employee) {
        for (Employee element : employees) {
            if (element.getId() == employee.getId()) {
                return Response.status(Response.Status.CONFLICT).build();
            }
        }
        employees.add(employee);
        return Response.ok(employee).build();
    }

    @DELETE
    @Path("{employeeId}")
    public Response deleteEmployee(@PathParam("employeeId") int employeeId) {
        Employee employee = findById(employeeId);
        if (employee == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        employees.remove(employee);
        return Response.ok().build();
    }

    private Employee findById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        return id + name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Department) && (id == ((Department) obj).getId()) && (name.equals(((Department) obj).getName()));
    }
}