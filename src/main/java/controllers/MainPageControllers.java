package controllers;

import entities.FlightSchedule;
import entities.Plane;
import entities.User;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import repositories.FlightScheduleRepository;
import services.FlightScheduleServices;


import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainPageControllers implements Initializable {

    @FXML
    public Button showFlights;
    @FXML
    public Label nameLabel;
    @FXML
    public TableColumn<FlightSchedule,Long> idColumn;
    @FXML
    public TableColumn<FlightSchedule,String> dateColumn;
    @FXML
    public TableColumn<FlightSchedule,String> fromColumn;
    @FXML
    public TableColumn<FlightSchedule,String> toColumn;
    @FXML
    public TableColumn<FlightSchedule,String> planeNumberColumn;
    @FXML
    public TableColumn<FlightSchedule,String> companyColumn;
    @FXML
    public TableView<FlightSchedule> tableMainPage;
    @FXML
    private Button myOrdersMainPage;
    @FXML
    private Button usersMainPage;
    @FXML
    private Button planesMainPage;
    @FXML
    private ListView<String> scheduleList;
    @FXML
    private Button deleteMainPage;
    @FXML
    private Button orderMainPage;

    protected User user;
    ObservableList<FlightSchedule> flights;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FlightScheduleRepository flightScheduleRepository = new FlightScheduleRepository();
        flights = FXCollections.observableArrayList(flightScheduleRepository.findAll());
        idColumn.setCellValueFactory(new PropertyValueFactory<FlightSchedule, Long>("id"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<FlightSchedule, String>("locationFrom"));
        toColumn.setCellValueFactory(new PropertyValueFactory<FlightSchedule, String>("locationTo"));
        planeNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlane().getNumber()));
        companyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlane().getCompanyName()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightDateToString()));

        tableMainPage.setItems(flights);
    }

    public void setUser(User user) {
        this.user = user;
        nameLabel.setText("Hi, " + user.getUserName());
    }

    public void addFlightsScheduleFromFile(String path) {
        FlightScheduleServices flightScheduleServices = new FlightScheduleServices();
        FlightScheduleRepository flightScheduleRepository = new FlightScheduleRepository();
        try {
            flightScheduleRepository.saveOrUpdate(
                    flightScheduleServices.getFlightsFromFile(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
