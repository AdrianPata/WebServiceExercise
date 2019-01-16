package ro.pata.ws.test.tomcat;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(StudentAdapter.class)
public interface Student {
    String getName();
}
