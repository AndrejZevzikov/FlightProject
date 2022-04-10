package repositories;

import entities.User;
import interfaces.Crud;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.List;

public class UserRepository implements Crud<User> {

    @Override
    public List<User> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<User> userList = session.createQuery("Select u from User u",User.class).getResultList();
        session.close();
        return userList;
    }

    @Override
    public User findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        User user = session.find(User.class,id);
        session.close();
        return user;
    }
}