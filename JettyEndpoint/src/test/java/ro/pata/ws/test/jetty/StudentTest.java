package ro.pata.ws.test.jetty;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import java.util.Map;

public class StudentTest {
    private static QName SERVICE_NAME = new QName("http://test.ws.pata.ro/", "WsInt");
    private static QName PORT_NAME = new QName("http://test.ws.pata.ro/", "WsIntPort");

    private Service service;
    private WsInt wsProxy;
    private WsImpl wsImpl;

    {
        service = Service.create(SERVICE_NAME);
        String endpointAddress = "http://localhost:8080/service";
        service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
    }

    @Before
    public void reinstantiateWsInstances() {
        wsImpl = new WsImpl();
        wsProxy = service.getPort(PORT_NAME, WsInt.class);
    }

    @Test
    public void whenUsingHelloMethod_thenCorrect() {
        String endpointResponse = wsProxy.hello("Adi");
        String localResponse = wsImpl.hello("Adi");
        Assert.assertEquals(localResponse, endpointResponse);
    }

    @Test
    public void whenUsingHelloStudentMethod_thenCorrect() {
        Student student = new StudentImpl("John Doe");
        String endpointResponse = wsProxy.helloStudent(student);
        String localResponse = wsImpl.helloStudent(student);
        Assert.assertEquals(localResponse, endpointResponse);
    }

    @Test
    public void usingGetStudentsMethod_thenCorrect() {
        Student student1 = new StudentImpl("Adam");
        wsProxy.helloStudent(student1);

        Student student2 = new StudentImpl("Eve");
        wsProxy.helloStudent(student2);

        Map<Integer, Student> students = wsProxy.getStudents();
        Assert.assertEquals("Adam", students.get(1).getName());
        Assert.assertEquals("Eve", students.get(2).getName());
    }
}
