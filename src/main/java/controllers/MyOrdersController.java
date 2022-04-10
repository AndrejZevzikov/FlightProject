package controllers;

import entities.*;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import repositories.FlightOrderRepository;
import repositories.FlightScheduleRepository;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MyOrdersController implements Initializable {

    @FXML
    public Button scheduleButton;
    @FXML
    public Button usersButton;
    @FXML
    public Button planesButton;
    @FXML
    public TableView<FlightOrder> myOrderTable;
    @FXML
    public TableColumn<FlightOrder,Long> userIdColumn;
    @FXML
    public TableColumn<FlightOrder,String> fromColumn;
    @FXML
    public TableColumn<FlightOrder,String> toColumn;
    @FXML
    public TableColumn<FlightOrder, String> dateColumn;
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
    @FXML
    public Button printSelectedFile;
    @FXML
    public TextField insertFullName;
    @FXML
    public TextField insertIdentityNumber;
    @FXML
    public TextField insertAge;
    @FXML
    public TableColumn<Passenger,String> passengerName;
    @FXML
    public TableColumn<FlightOrder,Long> orderIDColumn;

    private User user;
    private ScenesController scenesController = new ScenesController();
    ObservableList<FlightOrder> flightOrders;
    private FlightOrderRepository flightOrderRepository = new FlightOrderRepository();
    private FlightScheduleRepository flightScheduleRepository = new FlightScheduleRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flightOrders = FXCollections.observableArrayList(flightOrderRepository.findAll());
        userIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUser().getId()));
//        fromColumn.setCellValueFactory(cellData -> new SimpleListProperty<FlightSchedule>())
//        toColumn.setCellValueFactory(new PropertyValueFactory<FlightSchedule, String>("locationTo"));
//        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightDateToString()));
//        passengerColumn.setCellValueFactory(new PropertyValueFactory<Passenger, String>("fullName"));
        orderIDColumn.setCellValueFactory(new PropertyValueFactory<FlightOrder, Long>("id"));

        myOrderTable.setItems(flightOrders);

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
