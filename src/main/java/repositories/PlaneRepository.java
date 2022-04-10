package repositories;

import entities.Plane;
import interfaces.Crud;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.List;

public class PlaneRepository implements Crud<Plane> {

    @Override
    public List<Plane> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Plane> planeList = session.createQuery("Select p from Plane p",Plane.class).getResultList();
        session.close();
        return planeList;
    }

    @Override
    public Plane findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Plane plane = session.find(Plane.class,id);
        session.close();
        return plane;
    }
}