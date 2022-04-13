package controllers;

import com.itextpdf.text.DocumentException;
import entities.*;
import interfaces.AuthenticatedPages;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import repositories.UserOrderRepository;
import repositories.TicketRepository;
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

public class MyOrdersController implements Initializable, AuthenticatedPages {

    @FXML
    public Button scheduleButton;
    @FXML
    public Button usersButton;
    @FXML
    public Button planesButton;
    @FXML
    public Button myOrdersButton;
    @FXML
    private TableView<Ticket> myOrdersTable;
    @FXML
    public TableColumn<Ticket, Long> userIdColumn;
    @FXML
    public TableColumn<Ticket, String> fromColumn;
    @FXML
    public TableColumn<Ticket, String> toColumn;
    @FXML
    public TableColumn<Ticket, String> dateColumn;
    @FXML
    public TableColumn<Ticket, String> passengerColumn;
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
    public TableColumn<Ticket, Long> orderIDColumn;
    @FXML
    public Label userLabel;
    @FXML
    public DatePicker dayOfBirth;

    private User user;
    private ScenesController scenesController = new ScenesController();
    private UserOrderRepository userOrderRepository = new UserOrderRepository();
    private TicketRepository ticketRepository = new TicketRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUpMyOrdersTable() {
        List<Ticket> ticketByUser = ticketRepository.findAll().stream()
                .filter(orderedFlights -> orderedFlights.getUserOrder().getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
        ObservableList<Ticket> flightOrders = FXCollections.observableArrayList(ticketByUser);

        userIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUserOrder().getUser().getId()));
        fromColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlight().getLocationFrom()));
        toColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlight().getLocationTo()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlight().getFlightDateToString()));
        passengerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassenger().getFullName()));
        orderIDColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        myOrdersTable.setItems(flightOrders);
    }

    public void removeSelectedOrder(ActionEvent event) throws IOException {
        ticketRepository.delete(myOrdersTable.getSelectionModel().getSelectedItem());
        scenesController.switchSceneToMyOrdersPage(event, user);
    }

    public void removeByIdButton(ActionEvent event) throws IOException {
        ticketRepository.deleteById(Long.parseLong(removeIdTextField.getText()));
        scenesController.switchSceneToMyOrdersPage(event, user);
    }

    public void addPassengerToFlights(ActionEvent event) throws IOException {
        if (isValidUserInput()) {
            Passenger passengerToAdd = Passenger.builder()
                    .fullName(insertFullName.getText())
                    .identityNumber(insertIdentityNumber.getText())
                    .DayOfBirth(convertLocalDateToDateObj(dayOfBirth.getValue()))
                    .build();

            PassengerValidatorService passengerValidatorService = new PassengerValidatorService();
            passengerValidatorService.addPassengerToFlight(passengerToAdd, myOrdersTable.getSelectionModel().getSelectedItem());
            scenesController.switchSceneToMyOrdersPage(event, user);
            insertFullName.clear();
            insertIdentityNumber.clear();
        }
    }

    private boolean isValidUserInput() {
        if (insertFullName.getText().isEmpty()
                || insertIdentityNumber.getText().isEmpty()
                || dayOfBirth.getValue() == null
                || myOrdersTable.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Fill all values");
            return false;
        }
        return true;
    }

    private Date convertLocalDateToDateObj(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }

    public void showTicketOfSelectedOrder(ActionEvent event) throws DocumentException, IOException, URISyntaxException {
        TicketPdfPrinter ticketPdfPrinter = new TicketPdfPrinter();
        Desktop desktop = Desktop.getDesktop();
        File file = ticketPdfPrinter.getTicketFile(myOrdersTable.getSelectionModel().getSelectedItem());
        desktop.open(file);
    }

    public void onUsersButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToUsersPage(event, user);
    }

    public void onPlanesButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToPlanesPage(event, user);
    }

    public void onScheduleButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToSchedulePage(event, user);
    }

    @Override
    public void onMyOrdersButton(ActionEvent event) {
        myOrdersButton.setDisable(true);
    }
}
