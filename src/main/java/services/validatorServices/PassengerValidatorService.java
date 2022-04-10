package services.validatorServices;

import entities.OrderedFlights;
import entities.Passenger;
import javafx.scene.control.Label;
import repositories.OrderedFlightsRepository;
import repositories.PassengerRepository;

import java.util.Optional;

public class PassengerValidatorService {

    private PassengerRepository passengerRepository = new PassengerRepository();

    public void addPassengerToFlight(Passenger passenger, OrderedFlights orderedFlight){
        OrderedFlightsRepository orderedFlightsRepository = new OrderedFlightsRepository();
        Optional<Passenger> existingPassengerFromDB = passengerRepository.findAll().stream()
                .filter(passenger1 -> passenger.getIdentityNumber().equals(passenger1.getIdentityNumber()))
                .findFirst();

        if (existingPassengerFromDB.isPresent()){
            orderedFlight.setPassenger(existingPassengerFromDB.get());
        } else {
            orderedFlight.setPassenger(passenger);
        }
        orderedFlightsRepository.saveOrUpdate(orderedFlight);
    }
}
