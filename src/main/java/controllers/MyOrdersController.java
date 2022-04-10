package controllers;

import entities.FlightOrder;
import entities.FlightSchedule;
import entities.Passenger;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MyOrdersController implements Initializable {

    @FXML
    public Button scheduleButton;
    @FXML
    public Button usersButton;
    @FXML
    public Button planesButton;
    @FXML
    public TableView<FlightOrder> muOrderTable;
    @FXML
    public TableColumn<User,Long> userIdColumn;
    @FXML
    public TableColumn<FlightSchedule,String> fromColumn;
    @FXML
    public TableColumn<FlightSchedule,String> toColumn;
    @FXML
    public TableColumn<FlightSchedule, Date> dateColumn;
    @FXML
    public TableColumn<Passenger,String> passengerColumn;
    @FXML
    public TextField insertFullNameTextField;
    @FXML
    public TextField insertIdentityNumberTextField;
    @FXML
    public TextField insertAgeTextField;
    @FXML
    public Label errorLabel;
    @FXML
    public Button addButton;
    @FXML
    public Button removeSelectedOrder;
    @FXML
    public Button removeOrderByIdButton;
    @FXML
    public TextField removeIdTextField;

    private User user;
    private ScenesController scenesController = new ScenesController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUser(User user) {
        this.user = user;
    }

    public void onUsersButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToUsersPage(event, user);
    }

    public void onPlanesButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToPlanesPage(event,user);
    }

    public void onScheduleButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToMainPage(event, user);
    }
}
