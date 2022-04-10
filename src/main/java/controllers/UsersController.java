package controllers;

import com.sun.xml.bind.v2.TODO;
import constants.Pages;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import repositories.UserRepository;
import services.UserValidationService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    @FXML
    public Label userLabel;
    @FXML
    public Button scheduleButton;
    @FXML
    public Label errorLabel;
    @FXML
    public TextField idTextField;
    @FXML
    public Button deleteSelectionButton;
    @FXML
    public Button addButton;
    @FXML
    public TextField emailTextField;
    @FXML
    public TextField passwordTextField;
    @FXML
    public TextField usernameTextFields;
    @FXML
    public Button deleteByIdButton;
    @FXML
    public Button planesButton;
    @FXML
    public TableView<User> usersTable;
    @FXML
    public TableColumn<User, Long> userIdColumn;
    @FXML
    public TableColumn<User, String> usernameColumn;
    @FXML
    public TableColumn<User, String> emailColumn;

    private User user;

    ObservableList<User> users;
    private UserRepository userRepository = new UserRepository();
    private ScenesController scenesController = new ScenesController();
    private UserValidationService userValidationService = new UserValidationService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users = FXCollections.observableArrayList(userRepository.findAll());
        userIdColumn.setCellValueFactory(new PropertyValueFactory<User, Long>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        usersTable.setItems(users);
    }

    public void onAddButton(ActionEvent event) throws IOException {
       if (userValidationService.isUserInputValid(createUserFromInput(),errorLabel)){
           saveAndRefresh(event);
       }
    }

    public void onScheduleButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToMainPage(event,user);
    }

    public void deleteSelectedUser(ActionEvent event) throws IOException {
        userRepository.delete(usersTable.getSelectionModel().getSelectedItem());
        scenesController.changeSceneToUsersPage(event, user);
    }

    public void deleteUserById(ActionEvent event) throws IOException {
        userRepository.deleteById(Long.parseLong(idTextField.getText()));
        scenesController.changeSceneToUsersPage(event,user);
    }

    private User createUserFromInput(){
        return User.builder()
                .userName(usernameTextFields.getText())
                .password(passwordTextField.getText())
                .email(emailTextField.getText())
                .build();
    }

    private void saveAndRefresh(ActionEvent event) throws IOException {
        userRepository.saveOrUpdate(createUserFromInput());
        scenesController.changeSceneToUsersPage(event, user);
    }

    public void setUser(User user) {
        this.user = user;
        userLabel.setText("Hi, " + user.getUserName());
    }
    public void onPlanesButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToPlanesPage(event,user);
    }

    public void onMyOrdersButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToMyOrdersPage(event,user);
    }
}
