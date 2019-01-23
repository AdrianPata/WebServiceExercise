package ro.pata.ws.test.tomcat.servlet;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ro.pata.ws.test.tomcat.db.HibernateSessionFactoryUtil;
import ro.pata.ws.test.tomcat.db.NumeEntity;
import ro.pata.ws.test.tomcat.db.PrenomsEntity;
import ro.pata.ws.test.tomcat.db.TelefonEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class dbadd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out=response.getWriter();

        Session session= HibernateSessionFactoryUtil.getSession();

        Query q=session.createQuery("select p from PrenomsEntity p order by RAND()");
        q.setMaxResults(2);

        List<PrenomsEntity> numeList= q.getResultList();
        String nume=numeList.get(0).getPrenom()+" "+numeList.get(1).getPrenom();

        out.println("Adding "+nume);

        Transaction tx=null;
        try{
            tx=session.beginTransaction();

            NumeEntity om=new NumeEntity();
            om.setNume(nume);
            om.setAgenda(new HashSet<TelefonEntity>());

            Random rand = new Random();
            int agendaItems=rand.nextInt(4)+1;
            for(int i=0;i<agendaItems;i++){
                TelefonEntity tel=new TelefonEntity();
                tel.setTelefon(getPhone());
                tel.setOwner(om);
                om.getAgenda().add(tel);
            }

            session.persist(om);

            tx.commit();
        } catch(Exception ex) {
            if(tx!=null) tx.rollback();
            out.println("Error: "+ex.getMessage());
            ex.printStackTrace();
        } finally {
            session.close();
        }
        out.println("Added");

        session.close();
    }

    private String getPhone(){
        Random rand = new Random();
        int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);

        DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
        DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

        return df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);
    }
}
