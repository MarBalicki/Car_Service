package car.service;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            System.out.println("Konfiguruję hiberneta.");
            Configuration configuration = new Configuration();
            configuration.configure("/hibernate.cfg.xml");
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (HibernateException he) {
            System.out.println(he.getMessage());
            System.exit(99);
            throw he;
        }
    }

    public static SessionFactory getOurSessionFactory() {
        return ourSessionFactory;
    }
}
