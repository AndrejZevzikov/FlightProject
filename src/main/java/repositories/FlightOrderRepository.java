package repositories;

import entities.FlightOrder;
import interfaces.Crud;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.List;

public class FlightOrderRepository implements Crud<FlightOrder> {

    @Override
    public List<FlightOrder> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<FlightOrder> flightList = session.createQuery("Select fo from FlightOrder fo",FlightOrder.class).getResultList();
        session.close();
        return flightList;
    }

    @Override
    public FlightOrder findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        FlightOrder flight =  session.find(FlightOrder.class,id);
        session.close();
        return flight;
    }
}
