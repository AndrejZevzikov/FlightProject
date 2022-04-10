package controllers;

import com.itextpdf.text.DocumentException;
import entities.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import repositories.FlightOrderRepository;
import repositories.OrderedFlightsRepository;
import services.TicketPdfPrinter;
import services.validatorServices.PassengerValidatorService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MyOrdersController implements Initializable {

    @FXML
    public Button scheduleButton;
    @FXML
    public Button usersButton;
    @FXML
    public Button planesButton;
    @FXML
    private TableView<OrderedFlights> myOrderTable;
    @FXML
    public TableColumn<OrderedFlights, Long> userIdColumn;
    @FXML
    public TableColumn<OrderedFlights,String> fromColumn;
    @FXML
    public TableColumn<OrderedFlights, String> toColumn;
    @FXML
    public TableColumn<OrderedFlights, String> dateColumn;
    @FXML
    public TableColumn<OrderedFlights, String> passengerColumn;
    @FXML
    private Label errorLabel;
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
    public TableColumn<OrderedFlights, Long> orderIDColumn;
    @FXML
    public Label userLabel;
    @FXML
    public DatePicker dayOfBirth;

    private User user;
    private ScenesController scenesController = new ScenesController();
    ObservableList<OrderedFlights> flightOrders;
    private FlightOrderRepository flightOrderRepository = new FlightOrderRepository();
    private OrderedFlightsRepository orderedFlightsRepository = new OrderedFlightsRepository();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUpMyOrdersTable(){

        List<OrderedFlights> orderedFlightsFilteredListByUser = orderedFlightsRepository.findAll().stream()
                .filter(orderedFlights -> orderedFlights.getFlightOrder().getUser().getId().equals(user.getId())).collect(Collectors.toList());
        flightOrders = FXCollections.observableArrayList(orderedFlightsFilteredListByUser);

        userIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFlightOrder().getUser().getId()));
        fromColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightSchedule().getLocationFrom()));
        toColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightSchedule().getLocationTo()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightSchedule().getFlightDateToString()));
        passengerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassenger().getFullName()));
        orderIDColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

        myOrderTable.setItems(flightOrders);
    }

    public void removeSelectedOrder(ActionEvent event) throws IOException {
        orderedFlightsRepository.delete(myOrderTable.getSelectionModel().getSelectedItem());
        scenesController.changeSceneToMyOrdersPage(event,user);
    }

    public void removeByIdButton(ActionEvent event) throws IOException {
        orderedFlightsRepository.deleteById(Long.parseLong(removeIdTextField.getText()));
        scenesController.changeSceneToMyOrdersPage(event,user);
    }

    public void addPassengerToFlights(ActionEvent event) throws IOException {
        PassengerValidatorService passengerValidatorService = new PassengerValidatorService();
        if (insertFullName.getText().isEmpty()
                || insertIdentityNumber.getText().isEmpty()
                || dayOfBirth.getValue() == null
                || myOrderTable.getSelectionModel().getSelectedItem() == null){
            errorLabel.setText("Fill all values");
        } else {
            Passenger temporaryPassenger = Passenger.builder()
                    .fullName(insertFullName.getText())
                    .identityNumber(insertIdentityNumber.getText())
                    .DayOfBirth(convertLocalDateToDateObj(dayOfBirth.getValue()))
                    .build();

            passengerValidatorService.addPassengerToFlight(temporaryPassenger,myOrderTable.getSelectionModel().getSelectedItem());
            scenesController.changeSceneToMyOrdersPage(event,user);
            insertFullName.clear();
            insertIdentityNumber.clear();
        }
    }

    private Date convertLocalDateToDateObj(LocalDate localDate){
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }

    public void printSelectedOrder(ActionEvent event) throws DocumentException, IOException, URISyntaxException {
        TicketPdfPrinter printer = new TicketPdfPrinter();
        Desktop desktop = Desktop.getDesktop();
        File file = printer.getTicketFile(myOrderTable.getSelectionModel().getSelectedItem());
        desktop.open(file);

    }

    public void onUsersButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToUsersPage(event, user);
    }

    public void onPlanesButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToPlanesPage(event, user);
    }

    public void onScheduleButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToMainPage(event, user);
    }
}
