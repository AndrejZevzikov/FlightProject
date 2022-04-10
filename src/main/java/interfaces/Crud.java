package interfaces;

import org.hibernate.Session;
import utils.HibernateUtils;
import java.util.List;

public interface Crud<T> {

    List<T> findAll();

    T findById(Long id);

    default void saveOrUpdate(T entity){
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
        session.close();
    }

    default void saveOrUpdate(List<T> entity){
        entity.forEach(t -> {
            Session session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(t);
            session.getTransaction().commit();
            session.close();
        });
    }

    default void delete(T entity){
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    default void deleteById(Long id){
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(findById(id));
        session.getTransaction().commit();
        session.close();
    }
}