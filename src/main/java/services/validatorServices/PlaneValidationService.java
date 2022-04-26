package services.validatorServices;

import entities.Plane;
import javafx.scene.control.Label;
import repositories.PlaneRepository;

import java.util.Optional;

public class PlaneValidationService {

    PlaneRepository planeRepository = new PlaneRepository();

    public boolean isPlaneValid(Plane plane, Label errorLabel) {
        Optional<Plane> invalidPlane = planeRepository.findAll().stream()
                .filter(plane1 -> plane1.getNumber().equals(plane.getNumber()))
                .filter(plane1 -> plane1.getCompanyName().equals(plane.getCompanyName()))
                .findAny();


        if (plane.getNumber().isEmpty() || plane.getCompanyName().isEmpty() || plane.getCapacity() == 0) {
            errorLabel.setText("Fill all values");
            return false;
        }
        if (invalidPlane.isPresent()) {
            errorLabel.setText("Plane already Exists");
            return false;
        }
        return true;
    }
}