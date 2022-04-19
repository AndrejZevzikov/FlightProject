package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import repositories.UserRepository;
import services.validatorServices.UserValidationService;

import java.io.IOException;

public class RegistrationController {
    @FXML
    public TextField usernameForRegistration;
    @FXML
    public TextField emailForRegistration;
    @FXML
    public Button confirmRegistrationButton;
    @FXML
    public TextField passwordForRegistration;
    @FXML
    private Label errorLabel;

    private ScenesController scenesController = new ScenesController();
    private UserRepository userRepository = new UserRepository();
    private UserValidationService userValidationService = new UserValidationService();

    public void confirmRegistration(ActionEvent event) throws IOException {

       if(userValidationService.isUserInputValid(createUserFromInput(), errorLabel)) {
           saveAndLogin(event);
       }
    }

    public void onBackToSignInButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToLoginPage(event);
    }

    private User createUserFromInput(){
        return User.builder()
                .userName(usernameForRegistration.getText())
                .password(passwordForRegistration.getText())
                .email(emailForRegistration.getText())
                .build();
    }

    private void saveAndLogin(ActionEvent event) throws IOException {
        userRepository.saveOrUpdate(createUserFromInput());
        scenesController.switchSceneToLoginPage(event);
    }
}
