package repositories;

import entities.Flight;
import interfaces.Crud;
import org.hibernate.Session;
import utils.HibernateUtils;
import java.util.List;

public class FlightRepository implements Crud<Flight> {

    @Override
    public List<Flight> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Flight> flightsList = session.createQuery("Select fs from FlightSchedule fs", Flight.class).getResultList();
        session.close();
        return flightsList;
    }

    @Override
    public Flight findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Flight flight = session.find(Flight.class,id);
        session.close();
        return flight;
    }
}