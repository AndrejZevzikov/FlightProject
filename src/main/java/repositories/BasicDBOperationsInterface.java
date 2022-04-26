package repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateH2Utils;

import java.util.List;

public interface BasicDBOperationsInterface<T> {

    List<T> findAll();

    T findById(Long id);

    default void saveOrUpdate(T entity) {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
        session.close();

    }

    default void saveOrUpdate(List<T> entity) {
        entity.forEach(this::saveOrUpdate);
    }

    default void delete(T entity) {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    default void deleteById(Long id) {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(findById(id));
        session.getTransaction().commit();
        session.close();
    }
}