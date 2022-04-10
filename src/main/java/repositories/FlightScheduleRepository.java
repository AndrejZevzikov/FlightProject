package repositories;

import entities.FlightSchedule;
import interfaces.Crud;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.List;

public class FlightScheduleRepository implements Crud<FlightSchedule> {

    @Override
    public List<FlightSchedule> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<FlightSchedule> flightsList = session.createQuery("Select fs from FlightSchedule fs",FlightSchedule.class).getResultList();
        session.close();
        return flightsList;
    }

    @Override
    public FlightSchedule findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        FlightSchedule flight = session.find(FlightSchedule.class,id);
        session.close();
        return flight;
    }
}
