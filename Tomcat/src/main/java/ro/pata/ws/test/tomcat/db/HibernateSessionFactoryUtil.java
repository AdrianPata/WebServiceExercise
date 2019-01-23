package ro.pata.ws.test.tomcat.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory = null;

    public static Session getSession() {

        if (sessionFactory == null) {
            createSessionFactory();
        }


        return sessionFactory.openSession();
    }


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            createSessionFactory();
        }
        return sessionFactory;
    }


    private static void createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();

        sessionFactory = configuration.buildSessionFactory();
    }
}
