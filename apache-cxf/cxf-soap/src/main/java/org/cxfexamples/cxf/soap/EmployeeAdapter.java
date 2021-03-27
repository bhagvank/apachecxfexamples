package org.cxfexamples.cxf.soap;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class EmployeeAdapter extends XmlAdapter<EmployeeImpl, Employee> {
    public EmployeeImpl marshal(Employee Employee) throws Exception {
        if (Employee instanceof EmployeeImpl) {
            return (EmployeeImpl) Employee;
        }
        return new EmployeeImpl(Employee.getName());
    }

    public Employee unmarshal(EmployeeImpl Employee) throws Exception {
        return Employee;
    }
}