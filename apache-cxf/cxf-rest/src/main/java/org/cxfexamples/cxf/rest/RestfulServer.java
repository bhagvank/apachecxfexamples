package org.cxfexamples.cxf.rest;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

public class RestfulServer {
    public static void main(String args[]) throws Exception {
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(DepartmentRepository.class);
        factoryBean.setResourceProvider(new SingletonResourceProvider(new DepartmentRepository()));
        factoryBean.setAddress("http://localhost:8080/");
        Server server = factoryBean.create();

        System.out.println("Server Starting");
        Thread.sleep(1000 * 1000);
        System.out.println("Server Shutting Down");
        server.destroy();
        System.exit(0);
    }
}
