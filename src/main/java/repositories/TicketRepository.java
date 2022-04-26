package repositories;

import entities.Ticket;
import org.hibernate.Session;
import utils.HibernateH2Utils;

import java.util.List;

public class TicketRepository implements BasicDBOperationsInterface<Ticket> {
    @Override
    public List<Ticket> findAll() {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        List<Ticket> flightsList =
                session.createQuery("Select t from Ticket t", Ticket.class).getResultList();
        session.close();
        return flightsList;

    }

    @Override
    public Ticket findById(Long id) {
        Session session = HibernateH2Utils.getSessionFactory().openSession();
        Ticket flight = session.find(Ticket.class, id);
        session.close();
        return flight;
    }
}