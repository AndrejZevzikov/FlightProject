package repositories;

import entities.Plane;
import org.hibernate.Session;
import utils.HibernateH2Utils;

import java.util.List;

public class PlaneRepository implements BasicDBOperationsInterface<Plane> {

    @Override
    public List<Plane> findAll() {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        List<Plane> planeList = session.createQuery("Select p from Plane p", Plane.class).getResultList();
        session.close();
        return planeList;

    }

    @Override
    public Plane findById(Long id) {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        Plane plane = session.find(Plane.class, id);
        session.close();
        return plane;
    }
}