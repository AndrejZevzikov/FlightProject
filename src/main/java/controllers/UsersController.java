package controllers;

import entities.User;
import interfaces.AuthenticatedPagesInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import repositories.UserRepository;
import services.validatorServices.UserValidationService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersController implements Initializable, AuthenticatedPagesInterface {
    @FXML
    private Label userLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField usernameTextFields;
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, Long> userIdColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private Button userButton;

    private User user;
    private final UserRepository userRepository = new UserRepository();
    private final ScenesController scenesController = new ScenesController();
    private final UserValidationService userValidationService = new UserValidationService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<User> users = FXCollections.observableArrayList(userRepository.findAll());
        userIdColumn.setCellValueFactory(new PropertyValueFactory<User, Long>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        usersTable.setItems(users);
    }

    public void onAddButton(ActionEvent event) throws IOException {
        if (userValidationService.isUserInputValid(createUserFromInput(), errorLabel)) {
            saveAndRefresh(event);
        }
    }

    public void onScheduleButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToSchedulePage(event, user);
    }

    public void deleteSelectedUser(ActionEvent event) throws IOException {
        userRepository.delete(usersTable.getSelectionModel().getSelectedItem());
        scenesController.switchSceneToUsersPage(event, user);
    }

    public void deleteUserById(ActionEvent event) throws IOException {
        userRepository.deleteById(Long.parseLong(idTextField.getText()));
        scenesController.switchSceneToUsersPage(event, user);
    }

    public void setUser(User user) {
        this.user = user;
        userLabel.setText("Hi, " + user.getUserName());
    }

    @Override
    public void onUsersButton(ActionEvent event) {
        userButton.setDisable(true);
    }

    public void onPlanesButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToPlanesPage(event, user);
    }

    public void onMyOrdersButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToMyOrdersPage(event, user);
    }

    @Override
    public void onLogoutButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToLoginPage(event);
    }

    private User createUserFromInput() {
        return User.builder()
                .userName(usernameTextFields.getText())
                .password(passwordTextField.getText())
                .email(emailTextField.getText())
                .build();
    }

    private void saveAndRefresh(ActionEvent event) throws IOException {
        userRepository.saveOrUpdate(createUserFromInput());
        scenesController.switchSceneToUsersPage(event, user);
    }
}