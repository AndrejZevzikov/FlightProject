package controllers;

import constants.PagesPaths;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import repositories.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LoginController {

    @FXML
    public TextField usernameText;
    @FXML
    private Label signInLabel;
    @FXML
    private PasswordField passwordText;

    private final ScenesController scenesController = new ScenesController();
    private final UserRepository userRepository = new UserRepository();

    public void login(ActionEvent event) throws IOException {
        List<User> users = userRepository.findAll();
        Optional<User> userFromDB = users.stream()
                .filter(user -> user.getUserName().equals(usernameText.getText()))
                .findAny();

        if (!userFromDB.isPresent()) {
            signInLabel.setText("No such user");
        } else if (!userFromDB.get().isPasswordCorrect(passwordText.getText())) {
            signInLabel.setText("Wrong Password");
        } else {
            scenesController.switchSceneByGivenPath(event, userFromDB.get(), PagesPaths.SCHEDULE_PAGE);
        }
    }

    public void registration(ActionEvent event) throws IOException {
        scenesController.switchSceneByGivenPath(event, PagesPaths.REGISTRATION);
    }
}