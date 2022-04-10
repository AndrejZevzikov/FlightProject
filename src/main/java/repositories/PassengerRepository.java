package repositories;

import entities.Passenger;
import interfaces.Crud;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.List;

public class PassengerRepository implements Crud<Passenger> {

    @Override
    public List<Passenger> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Passenger> passengerList = session.createQuery("Select p from Passenger p",Passenger.class).getResultList();
        session.close();
        return passengerList;
    }

    @Override
    public Passenger findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Passenger passenger = session.find(Passenger.class,id);
        session.close();
        return passenger;
    }
}