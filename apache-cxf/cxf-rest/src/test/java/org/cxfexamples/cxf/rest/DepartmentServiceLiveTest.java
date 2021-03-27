package org.cxfexamples.cxf.rest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.bind.JAXB;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DepartmentServiceLiveTest {
    private static final String BASE_URL = "http://localhost:8080/departmentservice/departments/";
    private static CloseableHttpClient client;

    @BeforeClass
    public static void createClient() {
        client = HttpClients.createDefault();
    }

    @AfterClass
    public static void closeClient() throws IOException {
        client.close();
    }

    @Test
    public void whenUpdateNonExistentDepartment_thenReceiveNotFoundResponse() throws IOException {
        final HttpPut httpPut = new HttpPut(BASE_URL + "3");
        final InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("non_existent_department.xml");
        httpPut.setEntity(new InputStreamEntity(resourceStream));
        httpPut.setHeader("Content-Type", "text/xml");

        final HttpResponse response = client.execute(httpPut);
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void whenUpdateUnchangedDepartment_thenReceiveNotModifiedResponse() throws IOException {
        final HttpPut httpPut = new HttpPut(BASE_URL + "1");
        final InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("unchanged_department.xml");
        httpPut.setEntity(new InputStreamEntity(resourceStream));
        httpPut.setHeader("Content-Type", "text/xml");

        final HttpResponse response = client.execute(httpPut);
        assertEquals(304, response.getStatusLine().getStatusCode());
    }

    @Test
    public void whenUpdateValidDepartment_thenReceiveOKResponse() throws IOException {
        final HttpPut httpPut = new HttpPut(BASE_URL + "2");
        final InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("changed_department.xml");
        httpPut.setEntity(new InputStreamEntity(resourceStream));
        httpPut.setHeader("Content-Type", "text/xml");

        final HttpResponse response = client.execute(httpPut);
        assertEquals(200, response.getStatusLine().getStatusCode());

        final Department department = getDepartment(2);
        assertEquals(2, department.getId());
        assertEquals("Sales", department.getName());
    }

    @Test
    public void whenCreateConflictEmployee_thenReceiveConflictResponse() throws IOException {
        final HttpPost httpPost = new HttpPost(BASE_URL + "1/employees");
        final InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("conflict_employee.xml");
        httpPost.setEntity(new InputStreamEntity(resourceStream));
        httpPost.setHeader("Content-Type", "text/xml");

        final HttpResponse response = client.execute(httpPost);
        assertEquals(409, response.getStatusLine().getStatusCode());
    }

    @Test
    public void whenCreateValidEmployee_thenReceiveOKResponse() throws IOException {
        final HttpPost httpPost = new HttpPost(BASE_URL + "2/employees");
        final InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("created_employee.xml");
        httpPost.setEntity(new InputStreamEntity(resourceStream));
        httpPost.setHeader("Content-Type", "text/xml");

        final HttpResponse response = client.execute(httpPost);
        assertEquals(200, response.getStatusLine().getStatusCode());

        final Employee employee = getEmployee(2, 3);
        assertEquals(3, employee.getId());
        assertEquals("Employee C", employee.getName());
    }

    @Test
    public void whenDeleteInvalidEmployee_thenReceiveNotFoundResponse() throws IOException {
        final HttpDelete httpDelete = new HttpDelete(BASE_URL + "1/employees/3");
        final HttpResponse response = client.execute(httpDelete);
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void whenDeleteValidEmployee_thenReceiveOKResponse() throws IOException {
        final HttpDelete httpDelete = new HttpDelete(BASE_URL + "1/employees/1");
        final HttpResponse response = client.execute(httpDelete);
        assertEquals(200, response.getStatusLine().getStatusCode());

        final Department department = getDepartment(1);
        assertEquals(1, department.getEmployees().size());
        assertEquals(2, department.getEmployees().get(0).getId());
        assertEquals("Employee B", department.getEmployees().get(0).getName());
    }

    private Department getDepartment(int departmentOrder) throws IOException {
        final URL url = new URL(BASE_URL + departmentOrder);
        final InputStream input = url.openStream();
        return JAXB.unmarshal(new InputStreamReader(input), Department.class);
    }

    private Employee getEmployee(int departmentOrder, int employeeOrder) throws IOException {
        final URL url = new URL(BASE_URL + departmentOrder + "/employees/" + employeeOrder);
        final InputStream input = url.openStream();
        return JAXB.unmarshal(new InputStreamReader(input), Employee.class);
    }
}