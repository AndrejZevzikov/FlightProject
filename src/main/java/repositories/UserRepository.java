package repositories;

import entities.User;
import org.hibernate.Session;
import utils.HibernateH2Utils;

import java.util.List;

public class UserRepository implements BasicDBOperationsInterface<User> {

    @Override
    public List<User> findAll() {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        List<User> userList = session.createQuery("Select u from User u", User.class).getResultList();
        session.close();
        return userList;

    }

    @Override
    public User findById(Long id) {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        User user = session.find(User.class, id);
        session.close();
        return user;
    }
}