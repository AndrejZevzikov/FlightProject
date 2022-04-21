package repositories;

import entities.Flight;
import entities.Plane;
import interfaces.BasicDBOperationsInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateH2Utils;

import java.util.List;
import java.util.Optional;

public class FlightRepository implements BasicDBOperationsInterface<Flight> {

    @Override
    public List<Flight> findAll() {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        List<Flight> flightsList = session.createQuery("Select f from Flight f", Flight.class).getResultList();
        session.close();
        return flightsList;
    }

    @Override
    public Flight findById(Long id) {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        Flight flight = session.find(Flight.class, id);
        session.close();
        return flight;
    }

    @Override
    public void saveOrUpdate(Flight entity) {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(modifyIncorrectPlane(entity));
        transaction.commit();
        session.close();
    }

    private Flight modifyIncorrectPlane(Flight flight) {
        if (flight.getPlane().getId() == null) {
            return flight;
        }
        flight.setPlane(getCorrectPlane(flight.getPlane()));
        return flight;

    }

    private Plane getCorrectPlane(Plane plane) {
        PlaneRepository planeRepository = new PlaneRepository();
        Optional<Plane> planeFromDB = planeRepository.findAll().stream()
                .filter(plane1 -> plane1.getId().equals(plane.getId()))
                .findFirst();

        if (planeFromDB.isPresent()) {
            return planeFromDB.get();
        }
        plane.setId(null);
        return plane;
    }
}