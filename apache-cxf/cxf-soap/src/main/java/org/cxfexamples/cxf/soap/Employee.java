package org.cxfexamples.cxf.soap;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(EmployeeAdapter.class)
public interface Employee {
    public String getName();
}