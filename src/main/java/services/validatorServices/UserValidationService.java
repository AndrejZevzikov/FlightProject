package services.validatorServices;

import entities.User;
import javafx.scene.control.Label;
import repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserValidationService {

    private final UserRepository userRepository = new UserRepository();

    public boolean isUserInputValid(User userForValidate, Label errorLabel) {
        List<User> allUsersInDB = userRepository.findAll();


        Optional<User> invalidUsername = allUsersInDB.stream()
                .filter(user -> user.getUserName().equals(userForValidate.getUserName()))
                .findAny();

        Optional<User> invalidEmail = allUsersInDB.stream()
                .filter(user -> user.getEmail().equals(userForValidate.getEmail()))
                .findAny();

        if (userForValidate.getPassword().isEmpty()
                || userForValidate.getUserName().isEmpty()
                || userForValidate.getEmail().isEmpty()) {
            errorLabel.setText("Some fields, empty");
            return false;
        } else if (invalidUsername.isPresent()) {
            errorLabel.setText("username already exists");
            return false;
        } else if (invalidEmail.isPresent()) {
            errorLabel.setText("email already exists");
            return false;
        }
        return true;
    }
}