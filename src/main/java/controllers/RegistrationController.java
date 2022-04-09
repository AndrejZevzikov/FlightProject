package controllers;

import constants.Pages;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import repositories.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RegistrationController {
    @FXML
    public TextField usernameReg;
    @FXML
    public TextField emailReg;
    @FXML
    public Button confirmReg;
    @FXML
    public TextField passwordReg;
    @FXML
    private Label registrationLabel;

    private ScenesController scenesController = new ScenesController();

    public void confirmRegistration(ActionEvent event) throws IOException {
        String username = usernameReg.getText();
        String email = emailReg.getText();
        String password = passwordReg.getText();
        UserRepository userRepository = new UserRepository();

        List<User> allUsersInDB = userRepository.findAll();

        Optional<User> invalidUsername = allUsersInDB.stream()
                .filter(user -> user.getUserName().equals(username))
                .findAny();
        Optional<User> invalidEmail = allUsersInDB.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();

        if (password.isEmpty() || username.isEmpty() || email.isEmpty()) {
            registrationLabel.setText("Some fields, empty");
        } else if (invalidUsername.isPresent()) {
            registrationLabel.setText("username already exists");
        } else if (invalidEmail.isPresent()) {
            registrationLabel.setText("email already exists");
        } else {
            userRepository.saveOrUpdate(
                    User.builder()
                            .userName(username)
                            .password(password)
                            .email(email).build());

            scenesController.changeSceneByGivenPageNonReg(event, Pages.LOGIN);
        }
    }
}
