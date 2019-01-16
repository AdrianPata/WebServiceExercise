package ro.pata.ws.test.tomcat;

import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

@WebService
public interface WsInt {
    String hello(String name);

    String helloStudent(Student student);

    @XmlJavaTypeAdapter(StudentMapAdapter.class)
    Map<Integer, Student> getStudents();
}
