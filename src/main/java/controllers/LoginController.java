package controllers;

import constants.Pages;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import repositories.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class LoginController {

    public TextField usernameText;
    @FXML
    private Button loginButton;
    @FXML
    private Label signInLabel;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button registrationButton;

    private ScenesController scenesController = new ScenesController();


    public void login(ActionEvent event) throws IOException {
        String userName = usernameText.getText();
        UserRepository userRepository = new UserRepository();
        List<User> users = userRepository.findAll();
        Optional<User> userToValid = users.stream()
                .filter(user -> user.getUserName().equals(userName))
                .findAny();

        if (!userToValid.isPresent()){
            signInLabel.setText("No such user");
        } else if (!userToValid.get().getPassword().equals(passwordText.getText())){
            signInLabel.setText("Wrong Password");
        } else {
            scenesController.changeSceneToMainPage(event,userToValid.get());
        }
    }

    public void registration(ActionEvent event) throws IOException {
        scenesController.changeSceneByGivenPageNonReg(event, Pages.REGISTRATION);
    }
}
