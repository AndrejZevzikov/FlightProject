package repositories;

import entities.UserOrder;
import interfaces.BasicDBOperationsInterface;
import org.hibernate.Session;
import utils.H2Utils;

import java.util.List;

public class UserOrderRepository implements BasicDBOperationsInterface<UserOrder> {

    @Override
    public List<UserOrder> findAll() {
        Session session = H2Utils.getSessionFactory().openSession();
        List<UserOrder> flightList = session.createQuery("Select uo from UserOrder uo", UserOrder.class).getResultList();
        session.close();
        return flightList;
    }

    @Override
    public UserOrder findById(Long id) {
        Session session = H2Utils.getSessionFactory().openSession();
        UserOrder flight = session.find(UserOrder.class, id);
        session.close();
        return flight;
    }
}