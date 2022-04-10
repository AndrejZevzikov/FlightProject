package controllers;

import constants.Pages;
import entities.FlightOrder;
import entities.FlightSchedule;
import entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import repositories.FlightOrderRepository;
import repositories.FlightScheduleRepository;
import services.FlightScheduleServices;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageControllers implements Initializable {

    @FXML
    public Label nameLabel;
    @FXML
    public TableColumn<FlightSchedule, Long> idColumn;
    @FXML
    public TableColumn<FlightSchedule, String> dateColumn;
    @FXML
    public TableColumn<FlightSchedule, String> fromColumn;
    @FXML
    public TableColumn<FlightSchedule, String> toColumn;
    @FXML
    public TableColumn<FlightSchedule, String> planeNumberColumn;
    @FXML
    public TableColumn<FlightSchedule, String> companyColumn;
    @FXML
    public TableView<FlightSchedule> tableMainPage;
    @FXML
    public TableColumn<FlightSchedule, Boolean> checkBox;
    @FXML
    public Button confirmOrder;
    @FXML
    public Label countLabel;
    @FXML
    public Button addFlights;
    @FXML
    private Button myOrdersMainPage;
    @FXML
    private Button usersMainPage;
    @FXML
    private Button planesMainPage;
    @FXML
    private Button deleteMainPage;
    @FXML
    private Button orderMainPage;

    protected User user;
    ObservableList<FlightSchedule> flights;
    private List<FlightSchedule> buckedFlights = new ArrayList<>();
    private int count;
    private FlightScheduleRepository flightScheduleRepository = new FlightScheduleRepository();
    private ScenesController scenesController = new ScenesController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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



    public void insertFlightInBucket(ActionEvent event) {
        FlightSchedule orderedFlight = tableMainPage.getSelectionModel().getSelectedItem();
        buckedFlights.add(orderedFlight);
        count += 1;
        countLabel.setText(String.valueOf(count));
    }

    public void confirmOrder(ActionEvent event) {
        FlightOrder order = FlightOrder.builder()
                .flights(buckedFlights)
                .user(user)
                .build();
        FlightOrderRepository flightOrderRepository = new FlightOrderRepository();
        flightOrderRepository.saveOrUpdate(order);
        count = 0;
        countLabel.setText("");
    }

    public void onAddFlightsBottom(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load file");
        File file = fileChooser.showOpenDialog(addFlights.getScene().getWindow());
        addFlightsScheduleFromFile(file.getAbsolutePath());
        scenesController.changeSceneToMainPage(event,user);
    }

    private void addFlightsScheduleFromFile(String path) {
        FlightScheduleServices flightScheduleServices = new FlightScheduleServices();
        try {
            flightScheduleRepository.saveOrUpdate(
                    flightScheduleServices.getFlightsFromFile(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFlight(ActionEvent event) throws IOException {
        FlightSchedule flightSchedule = tableMainPage.getSelectionModel().getSelectedItem();
        flightScheduleRepository.delete(flightSchedule);
        ScenesController scenesController = new ScenesController();
        scenesController.changeSceneToMainPage(event, user);
    }

    public void onUsersButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToUsersPage(event, user);
    }

    public void onPlanesButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToPlanesPage(event,user);
    }

    public void onMyOrdersButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToMyOrdersPage(event,user);
    }
}
