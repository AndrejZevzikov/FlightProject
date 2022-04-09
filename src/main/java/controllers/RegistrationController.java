package controllers;

import constants.Pages;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import repositories.UserRepository;
import services.UserValidationService;

import java.io.IOException;

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
    private UserRepository userRepository = new UserRepository();
    private UserValidationService userValidationService = new UserValidationService();

    public void confirmRegistration(ActionEvent event) throws IOException {

       if(userValidationService.isUserInputValid(createUserFromInput(),registrationLabel)) {
           saveAndLogin(event);
       }
    }

    private User createUserFromInput(){
        return User.builder()
                .userName(usernameReg.getText())
                .password(passwordReg.getText())
                .email(emailReg.getText())
                .build();
    }


    private void saveAndLogin(ActionEvent event) throws IOException {
        userRepository.saveOrUpdate(
                User.builder()
                        .userName(usernameReg.getText())
                        .password(passwordReg.getText())
                        .email(emailReg.getText())
                        .build());

        scenesController.changeSceneByGivenPageNonReg(event, Pages.LOGIN);
    }
}
