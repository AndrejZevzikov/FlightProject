package services.validatorServices;

import entities.Ticket;
import entities.Passenger;
import repositories.TicketRepository;
import repositories.PassengerRepository;

import java.util.Optional;

public class PassengerValidatorService {

    private PassengerRepository passengerRepository = new PassengerRepository();

    public void addPassengerToFlight(Passenger passengerForValidation, Ticket ticket) {
        TicketRepository ticketRepository = new TicketRepository();
        Optional<Passenger> existingPassengerFromDB = passengerRepository.findAll().stream()
                .filter(passenger -> passengerForValidation.getIdentityNumber().equals(passenger.getIdentityNumber()))
                .findFirst();

        if (existingPassengerFromDB.isPresent()) {
            ticket.setPassenger(existingPassengerFromDB.get());
        } else {
            ticket.setPassenger(passengerForValidation);
        }
        ticketRepository.saveOrUpdate(ticket);
    }
}