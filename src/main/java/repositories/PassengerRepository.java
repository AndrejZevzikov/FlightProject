package repositories;

import entities.Passenger;
import interfaces.BasicDBOperationsInterface;
import org.hibernate.Session;
import utils.HibernateH2Utils;

import java.util.List;

public class PassengerRepository implements BasicDBOperationsInterface<Passenger> {

    @Override
    public List<Passenger> findAll() {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        List<Passenger> passengerList = session.createQuery("Select p from Passenger p", Passenger.class).getResultList();
        session.close();
        return passengerList;
    }

    @Override
    public Passenger findById(Long id) {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        Passenger passenger = session.find(Passenger.class, id);
        session.close();
        return passenger;
    }
}