package org.cxfexamples.cxf.soap;

import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String args[]) throws InterruptedException {
        EmployeeServiceImpl implementor = new EmployeeServiceImpl();
        String address = "http://localhost:8080/EmployeeService";
        Endpoint.publish(address, implementor);
        System.out.println("Server up and running");
        Thread.sleep(90 * 1000);
        System.out.println("Server shutting down");
        System.exit(0);
    }
}