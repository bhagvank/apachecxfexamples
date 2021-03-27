package org.cxfexamples.cxf.soap;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Employee")
public class EmployeeImpl implements Employee {
    private String name;

    EmployeeImpl() {
    }

    public EmployeeImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}