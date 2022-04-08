package controllers;

import entities.FlightSchedule;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import repositories.FlightScheduleRepository;
import services.FlightScheduleServices;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainPageControllers implements Initializable {

    @FXML
    public Button showFlights;
    @FXML
    public Label nameLabel;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FlightScheduleRepository flightScheduleRepository = new FlightScheduleRepository();

        List<String> flightsToString = flightScheduleRepository.findAll().stream()
                .map(FlightSchedule::toString)
                .collect(Collectors.toList());
        scheduleList.getItems().addAll(flightsToString);
    }

    public void setUser(User user) {
        this.user = user;
        nameLabel.setText("Hi, " + user.getUserName());
    }

    public void addFlightsScheduleFromFile(String path){
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
