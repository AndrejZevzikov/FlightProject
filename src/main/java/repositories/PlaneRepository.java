package repositories;

import entities.Plane;
import interfaces.BasicDBOperationsInterface;
import org.hibernate.Session;
import utils.H2Utils;

import java.util.List;

public class PlaneRepository implements BasicDBOperationsInterface<Plane> {

    @Override
    public List<Plane> findAll() {
        Session session = H2Utils.getSessionFactory().openSession();
        List<Plane> planeList = session.createQuery("Select p from Plane p", Plane.class).getResultList();
        session.close();
        return planeList;
    }

    @Override
    public Plane findById(Long id) {
        Session session = H2Utils.getSessionFactory().openSession();
        Plane plane = session.find(Plane.class, id);
        session.close();
        return plane;
    }
}