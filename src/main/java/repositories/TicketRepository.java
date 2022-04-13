package repositories;

import entities.Ticket;
import interfaces.Crud;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.util.List;

public class TicketRepository implements Crud<Ticket> {
    @Override
    public List<Ticket> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Ticket> flightsList =
                session.createQuery("Select orf from OrderedFlights orf", Ticket.class).getResultList();
        session.close();
        return flightsList;
    }

    @Override
    public Ticket findById(Long id) {
            Session session = HibernateUtils.getSessionFactory().openSession();
            Ticket flight = session.find(Ticket.class,id);
            session.close();
            return flight;
        }
}
