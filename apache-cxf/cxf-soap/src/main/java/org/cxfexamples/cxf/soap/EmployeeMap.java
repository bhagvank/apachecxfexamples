package org.cxfexamples.cxf.soap;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "EmployeeMap")
public class EmployeeMap {
    private List<EmployeeEntry> entries = new ArrayList<EmployeeEntry>();

    @XmlElement(nillable = false, name = "entry")
    public List<EmployeeEntry> getEntries() {
        return entries;
    }

    @XmlType(name = "EmployeeEntry")
    public static class EmployeeEntry {
        private Integer id;
        private Employee employee;

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public void setEmployee(Employee employee) {
            this.employee = employee;
        }

        public Employee getEmployee() {
            return employee;
        }
    }
}