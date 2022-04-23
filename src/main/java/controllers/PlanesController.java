package controllers;

import controllers.controllersIntefaces.Controller;
import entities.Plane;
import entities.User;
import interfaces.AuthenticatedPagesInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import repositories.PlaneRepository;
import services.validatorServices.PlaneValidationService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlanesController implements Initializable, AuthenticatedPagesInterface, Controller {

    @FXML
    private Label errorLabel;
    @FXML
    private TableView<Plane> planeTable;
    @FXML
    private TableColumn<Plane, Long> idColumn;
    @FXML
    private TableColumn<Plane, String> companyNameColumn;
    @FXML
    private TableColumn<Plane, String> planeNumberColumn;
    @FXML
    private TableColumn<Plane, Integer> capacityColumn;
    @FXML
    private TextField deleteByIdTextField;
    @FXML
    private TextField insertCompanyTextField;
    @FXML
    private TextField insertCapacityTextField;
    @FXML
    private TextField insertNumberTextField;
    @FXML
    private Button planesButton;

    private final ScenesController scenesController = new ScenesController();
    private final PlaneRepository planeRepository = new PlaneRepository();
    private final PlaneValidationService planeValidationService = new PlaneValidationService();
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Plane> planes = FXCollections.observableArrayList(planeRepository.findAll());
        idColumn.setCellValueFactory(new PropertyValueFactory<Plane, Long>("id"));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<Plane, String>("companyName"));
        planeNumberColumn.setCellValueFactory(new PropertyValueFactory<Plane, String>("number"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<Plane, Integer>("capacity"));
        planeTable.setItems(planes);
    }

    public void deleteSelectedPlane(ActionEvent event) throws IOException {
        planeRepository.delete(planeTable.getSelectionModel().getSelectedItem());
        planeTable.refresh();
    }

    public void onAddButton(ActionEvent event) throws IOException {
        if (planeValidationService.isPlaneValid(createPlaneFromInput(), errorLabel)) {
            saveAndRefresh(event);
        }
    }

    public void deletePlaneById(ActionEvent event) throws IOException {
        planeRepository.deleteById(Long.parseLong(deleteByIdTextField.getText()));
        planeTable.refresh();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void onScheduleButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToSchedulePage(event, user);
    }

    public void onUsersButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToUsersPage(event, user);
    }

    @Override
    public void onPlanesButton(ActionEvent event) {
        planesButton.setDisable(true);
    }

    public void onMyOrdersButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToMyOrdersPage(event, user);
    }

    @Override
    public void onLogoutButton(ActionEvent event) throws IOException {
        scenesController.switchSceneToLoginPage(event);
    }

    private Plane createPlaneFromInput() {
        return Plane.builder()
                .companyName(insertCompanyTextField.getText())
                .capacity(Integer.parseInt(insertCapacityTextField.getText()))
                .number(insertNumberTextField.getText())
                .build();
    }

    private void saveAndRefresh(ActionEvent event) throws IOException {
        planeRepository.saveOrUpdate(createPlaneFromInput());
        scenesController.switchSceneToPlanesPage(event, user);
    }
}