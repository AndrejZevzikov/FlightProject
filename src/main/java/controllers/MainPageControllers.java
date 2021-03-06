package controllers;

import constants.PagesPaths;
import entities.*;
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

public class MainPageControllers implements Initializable, AuthenticatedPagesInterface {

    @FXML
    private Label nameLabel;
    @FXML
    private TableColumn<Flight, Long> idColumn;
    @FXML
    private TableColumn<Flight, String> dateColumn;
    @FXML
    private TableColumn<Flight, String> fromColumn;
    @FXML
    private TableColumn<Flight, String> toColumn;
    @FXML
    private TableColumn<Flight, String> planeNumberColumn;
    @FXML
    private TableColumn<Flight, String> companyColumn;
    @FXML
    private TableView<Flight> scheduleTable;
    @FXML
    private TableColumn<Flight, Boolean> checkBox;
    @FXML
    private Button confirmOrder;
    @FXML
    private Label flightsInCartCounter;
    @FXML
    private Button addFlightsButton;
    @FXML
    private Button scheduleButton;

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
        scenesController.switchSceneByGivenPath(event, user, PagesPaths.SCHEDULE_PAGE);
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
        scenesController.switchSceneByGivenPath(event, user, PagesPaths.SCHEDULE_PAGE);
    }

    public void onUsersButton(ActionEvent event) throws IOException {
        scenesController.switchSceneByGivenPath(event, user, PagesPaths.USERS_PAGE);
    }

    public void onPlanesButton(ActionEvent event) throws IOException {
        scenesController.switchSceneByGivenPath(event, user, PagesPaths.PLANES_PAGE);
    }

    @Override
    public void onScheduleButton(ActionEvent event) {
        scheduleButton.setDisable(true);
    }

    public void onMyOrdersButton(ActionEvent event) throws IOException {
        scenesController.switchSceneByGivenPath(event, user, PagesPaths.MY_ORDERS_PAGE);
    }

    @Override
    public void onLogoutButton(ActionEvent event) throws IOException {
        scenesController.switchSceneByGivenPath(event, PagesPaths.LOGIN);
    }
}