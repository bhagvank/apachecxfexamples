package org.cxfexamples.cxf.soap;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class EmployeeMapAdapter extends XmlAdapter<EmployeeMap, Map<Integer, Employee>> {
    public EmployeeMap marshal(Map<Integer, Employee> boundMap) throws Exception {
        EmployeeMap valueMap = new EmployeeMap();
        for (Map.Entry<Integer, Employee> boundEntry : boundMap.entrySet()) {
            EmployeeMap.EmployeeEntry valueEntry = new EmployeeMap.EmployeeEntry();
            valueEntry.setEmployee(boundEntry.getValue());
            valueEntry.setId(boundEntry.getKey());
            valueMap.getEntries().add(valueEntry);
        }
        return valueMap;
    }

    public Map<Integer, Employee> unmarshal(EmployeeMap valueMap) throws Exception {
        Map<Integer, Employee> boundMap = new LinkedHashMap<Integer, Employee>();
        for (EmployeeMap.EmployeeEntry employeeEntry : valueMap.getEntries()) {
            boundMap.put(employeeEntry.getId(), employeeEntry.getEmployee());
        }
        return boundMap;
    }
}