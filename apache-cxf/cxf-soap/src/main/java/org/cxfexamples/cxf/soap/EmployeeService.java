package org.cxfexamples.cxf.soap;

import java.util.Map;

import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@WebService
public interface EmployeeService {
    public String message(String message);

    public String addEmployee(Employee employee);

    @XmlJavaTypeAdapter(EmployeeMapAdapter.class)
    public Map<Integer, Employee> getEmployees();
}