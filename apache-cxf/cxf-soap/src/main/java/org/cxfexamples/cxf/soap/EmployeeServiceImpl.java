package org.cxfexamples.cxf.soap;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.jws.WebService;

@WebService(endpointInterface = "org.cxfexamples.cxf.soap.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {
    private Map<Integer, Employee> employees = new LinkedHashMap<Integer, Employee>();

    public String message(String message) {
        return "Received " + message;
    }

    public String addEmployee(Employee employee) {
        employees.put(employees.size() + 1, employee);
        return "Add " + employee.getName();
    }

    public Map<Integer, Employee> getEmployees() {
        return employees;
    }
}