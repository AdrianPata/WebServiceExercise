package ro.pata.ws.exercise;

import javax.jws.WebService;
import java.util.LinkedHashMap;
import java.util.Map;

@WebService(endpointInterface = "ro.pata.ws.exercise.WsInt")
public class WsImpl implements WsInt {

    private Map<Integer, Student> students
            = new LinkedHashMap<Integer, Student>();

    public String hello(String name) {
        return "Hello " + name;
    }

    public String helloStudent(Student student) {
        students.put(students.size() + 1, student);
        return "Hello " + student.getName();
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }
}
