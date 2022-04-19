package controllers;

import entities.*;
import interfaces.AuthenticatedPages;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import repositories.UserOrderRepository;
import repositories.FlightRepository;
import services.fileReaderService.FlightServices;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageControllers implements Initializable, AuthenticatedPages {

    @FXML
    public Label nameLabel;
    @FXML
    public TableColumn<Flight, Long> idColumn;
    @FXML
    public TableColumn<Flight, String> dateColumn;
    @FXML
    public TableColumn<Flight, String> fromColumn;
    @FXML
    public TableColumn<Flight, String> toColumn;
    @FXML
    public TableColumn<Flight, String> planeNumberColumn;
    @FXML
    public TableColumn<Flight, String> companyColumn;
    @FXML
    public TableView<Flight> scheduleTable;
    @FXML
    public TableColumn<Flight, Boolean> checkBox;
    @FXML
    public Button confirmOrder;
    @FXML
    public Label flightsInCartCounter;
    @FXML
    public Button addFlightsButton;
    @FXML
    public Button scheduleButton;
    @FXML
    public Button logoutButton;

    private User user;
    private List<Ticket> flightsInCart = new ArrayList<>();
    private FlightRepository flightRepository = new FlightRepository();
    private ScenesController scenesController = new ScenesController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Flight> flights = FXCollections.observableArrayList(flightRepository.findAll());
        idColumn.setCellValueFactory(new PropertyValueFactory<Flight, Long>("id"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("locationFrom"));
        toColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("locationTo"));
        planeNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlane().getNumber()));
        companyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlane().getCompanyName()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightDateToString()));

        scheduleTable.setItems(flights);
    }

    public void setUser(User user) {
        this.user = user;
        nameLabel.setText("Hi, " + user.getUserName());
    }

    public void insertFlightsInCart(ActionEvent event) {
        Flight selectedFlight = scheduleTable.getSelectionModel().getSelectedItem();
        Passenger emptyPassenger = Passenger.builder().fullName("").build();
        flightsInCart.add(Ticket.builder().passenger(emptyPassenger).flight(selectedFlight).build());
        flightsInCartCounter.setText(String.valueOf(flightsInCart.size()));
    }

    public void confirmOrder(ActionEvent event) {

        UserOrder order = new UserOrder();
        order.setUser(user);
        flightsInCart.forEach(order::addTicket);
        UserOrderRepository userOrderRepository = new UserOrderRepository();
        userOrderRepository.saveOrUpdate(order);
        flightsInCartCounter.setText("");
        flightsInCart.clear();
    }

    public void onAddFlightsButton(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load file");
        File file = fileChooser.showOpenDialog(addFlightsButton.getScene().getWindow());
        addFlightsScheduleFromGivenPath(file.getAbsolutePath());
        scenesController.switchSceneToSchedulePage(event, user);
    }

    private void addFlightsScheduleFromGivenPath(String path) {
        FlightServices flightServices = new FlightServices();
        try {
            flightRepository.saveOrUpdate(flightServices.getFlightsFromFile(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteFlightButton(ActionEvent event) throws IOException {
        flightRepository.delete(scheduleTable.getSelectionModel().getSelectedItem());
        scenesController.switchSceneToSchedulePage(event, user);
    }

    public void onUsersButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToUsersPage(event, user);
    }

    public void onPlanesButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToPlanesPage(event, user);
    }

    @Override
    public void onScheduleButton(ActionEvent event) {
        scheduleButton.setDisable(true);
    }

    public void onMyOrdersButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToMyOrdersPage(event, user);
    }

    @Override
    public void onLogoutButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToLoginPage(event);
    }
}
