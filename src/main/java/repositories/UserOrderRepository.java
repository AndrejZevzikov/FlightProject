package repositories;

import entities.UserOrder;
import interfaces.Crud;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.List;

public class UserOrderRepository implements Crud<UserOrder> {

    @Override
    public List<UserOrder> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<UserOrder> flightList = session.createQuery("Select fo from FlightOrder fo", UserOrder.class).getResultList();
        session.close();
        return flightList;
    }

    @Override
    public UserOrder findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        UserOrder flight =  session.find(UserOrder.class,id);
        session.close();
        return flight;
    }

}
