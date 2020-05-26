package car.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class EntityDao<T> {

    protected void saveOrUpdate(T entity) {
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        } catch (HibernateException he) {
            he.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
