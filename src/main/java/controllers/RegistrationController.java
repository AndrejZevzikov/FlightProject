package controllers;

import constants.PagesPaths;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.data.domain.Page;
import repositories.UserRepository;
import services.validatorServices.UserValidationService;

import java.io.IOException;

public class RegistrationController {

    @FXML
    private TextField usernameForRegistration;
    @FXML
    private TextField emailForRegistration;
    @FXML
    private TextField passwordForRegistration;
    @FXML
    private Label errorLabel;

    private final ScenesController scenesController = new ScenesController();
    private final UserRepository userRepository = new UserRepository();
    private final UserValidationService userValidationService = new UserValidationService();

    public void confirmRegistration(ActionEvent event) throws IOException {

        if (userValidationService.isUserInputValid(createUserFromInput(), errorLabel)) {
            saveAndLogin(event);
        }
    }

    public void onBackToSignInButton(ActionEvent event) throws IOException {
        scenesController.switchSceneByGivenPath(event, PagesPaths.LOGIN);
    }

    private User createUserFromInput() {
        return User.builder()
                .userName(usernameForRegistration.getText())
                .password(passwordForRegistration.getText())
                .email(emailForRegistration.getText())
                .build();
    }

    private void saveAndLogin(ActionEvent event) throws IOException {
        userRepository.saveOrUpdate(createUserFromInput());
        onBackToSignInButton(event);
    }
}