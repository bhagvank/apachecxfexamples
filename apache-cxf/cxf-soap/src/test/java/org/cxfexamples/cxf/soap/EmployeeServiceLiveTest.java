package org.cxfexamples.cxf.soap;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.junit.Before;
import org.junit.Test;

public class EmployeeServiceLiveTest {
    private static QName SERVICE_NAME = new QName("http://cxfexamples.com/", "EmployeeService");
    private static QName PORT_NAME = new QName("http://cxfexamples.com/", "EmployeeServicePort");

    private Service service;
    private EmployeeService employeeServiceProxy;
    private EmployeeServiceImpl employeeServiceImpl;

    {
        service = Service.create(SERVICE_NAME);
        final String endpointAddress = "http://localhost:8080/EmployeeService";
        service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
    }

    @Before
    public void reinstantiateEmployeeServiceInstances() {
        employeeServiceImpl = new EmployeeServiceImpl();
        employeeServiceProxy = service.getPort(PORT_NAME, EmployeeService.class);
    }

    @Test
    public void whenUsingMessageMethod_thenCorrect() {
        final String endpointResponse = employeeServiceProxy.message("Greetings");
        final String localResponse = employeeServiceImpl.message("Greetings");
        assertEquals(localResponse, endpointResponse);
    }

    @Test
    public void whenUsingAddEmployeeMethod_thenCorrect() {
        final Employee employee = new EmployeeImpl("Thomas Smith");
        final String endpointResponse = employeeServiceProxy.addEmployee(employee);
        final String localResponse = employeeServiceImpl.addEmployee(employee);
        assertEquals(localResponse, endpointResponse);
    }

    @Test
    public void usingGetEmployeesMethod_thenCorrect() {
        final Employee employee1 = new EmployeeImpl("Thomas");
        employeeServiceProxy.addEmployee(employee1);

        final Employee employee2 = new EmployeeImpl("Sam");
        employeeServiceProxy.addEmployee(employee2);

        final Map<Integer, Employee> employees = employeeServiceProxy.getEmployees();
        assertEquals("Thomas", employees.get(1).getName());
        assertEquals("Sam", employees.get(2).getName());
    }
}