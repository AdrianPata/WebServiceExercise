package ro.pata.ws.test.jetty;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StudentAdapter extends XmlAdapter<StudentImpl,Student> {
    public Student unmarshal(StudentImpl student) throws Exception {
        return student;
    }

    public StudentImpl marshal(Student student) throws Exception {
        if (student instanceof StudentImpl) {
            return (StudentImpl) student;
        }
        return new StudentImpl(student.getName());
    }
}
