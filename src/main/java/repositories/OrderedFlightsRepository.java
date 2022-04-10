package repositories;

import entities.FlightSchedule;
import entities.OrderedFlights;
import interfaces.Crud;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.List;

public class OrderedFlightsRepository implements Crud<OrderedFlights> {
    @Override
    public List<OrderedFlights> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<OrderedFlights> flightsList =
                session.createQuery("Select orf from OrderedFlights orf", OrderedFlights.class).getResultList();
        session.close();
        return flightsList;
    }

    @Override
    public OrderedFlights findById(Long id) {
            Session session = HibernateUtils.getSessionFactory().openSession();
            OrderedFlights flight = session.find(OrderedFlights.class,id);
            session.close();
            return flight;
        }
}
