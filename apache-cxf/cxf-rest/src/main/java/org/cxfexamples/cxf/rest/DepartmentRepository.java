package org.cxfexamples.cxf.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("departmentservice")
@Produces("text/xml")
public class DepartmentRepository {
    private Map<Integer, Department> departments = new HashMap<>();

    {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        employee1.setId(1);
        employee1.setName("Employee A");
        employee2.setId(2);
        employee2.setName("Employee B");

        List<Employee> department1Employees = new ArrayList<>();
        department1Employees.add(employee1);
        department1Employees.add(employee2);

        Department department1 = new Department();
        Department department2 = new Department();
        department1.setId(1);
        department1.setName("Accounting");
        department1.setEmployees(department1Employees);
        department2.setId(2);
        department2.setName("Finance");

        departments.put(1, department1);
        departments.put(2, department2);
    }

    @GET
    @Path("departments/{departmentId}")
    public Department getDepartment(@PathParam("departmentId") int departmentId) {
        return findById(departmentId);
    }

    @PUT
    @Path("departments/{departmentId}")
    public Response updateDepartment(@PathParam("departmentId") int departmentId, Department department) {
        Department existingDepartment = findById(departmentId);
        if (existingDepartment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (existingDepartment.equals(department)) {
            return Response.notModified().build();
        }
        departments.put(departmentId, department);
        return Response.ok().build();
    }

    @Path("departments/{departmentId}/employees")
    public Department pathToEmployee(@PathParam("departmentId") int departmentId) {
        return findById(departmentId);
    }

    private Department findById(int id) {
        for (Map.Entry<Integer, Department> department : departments.entrySet()) {
            if (department.getKey() == id) {
                return department.getValue();
            }
        }
        return null;
    }
}
