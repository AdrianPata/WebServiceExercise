package ro.pata.ws.test.tomcat.servlet;

import org.hibernate.Session;
import ro.pata.ws.test.tomcat.db.HibernateSessionFactoryUtil;
import ro.pata.ws.test.tomcat.db.NumeEntity;
import ro.pata.ws.test.tomcat.db.TelefonEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class dbquery extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        PrintWriter out=response.getWriter();
        out.println("Salut post");
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        PrintWriter out=response.getWriter();
        out.println("Persoane in agenda: ");

        Session session= HibernateSessionFactoryUtil.getSession();

        List<NumeEntity> lista=session.createQuery("select n from NumeEntity n").getResultList();

        for(NumeEntity n:lista){
            String s=n.getNume()+":";
            for(TelefonEntity t:n.getAgenda()){
                s+=t.getTelefon()+" ";
            }
            out.println(s);
        }

        session.close();
    }
}
