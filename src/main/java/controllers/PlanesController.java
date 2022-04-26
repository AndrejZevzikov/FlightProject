package controllers;

import constants.PagesPaths;
import entities.Plane;
import entities.User;
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

public class PlanesController implements Initializable, AuthenticatedPagesInterface {

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
        scenesController.switchSceneByGivenPath(event, user, PagesPaths.PLANES_PAGE);
    }

    public void onAddButton(ActionEvent event) throws IOException {
        if (planeValidationService.isPlaneValid(createPlaneFromInput(), errorLabel)) {
            saveAndRefresh(event);
        }
    }

    public void deletePlaneById(ActionEvent event) throws IOException {
        planeRepository.deleteById(Long.parseLong(deleteByIdTextField.getText()));
        scenesController.switchSceneByGivenPath(event, user, PagesPaths.PLANES_PAGE);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void onScheduleButton(ActionEvent event) throws IOException {
        scenesController.switchSceneByGivenPath(event, user, PagesPaths.SCHEDULE_PAGE);
    }

    public void onUsersButton(ActionEvent event) throws IOException {
        scenesController.switchSceneByGivenPath(event, user, PagesPaths.USERS_PAGE);
    }

    @Override
    public void onPlanesButton(ActionEvent event) {
        planesButton.setDisable(true);
    }

    public void onMyOrdersButton(ActionEvent event) throws IOException {
        scenesController.switchSceneByGivenPath(event, user, PagesPaths.MY_ORDERS_PAGE);
    }

    @Override
    public void onLogoutButton(ActionEvent event) throws IOException {
        scenesController.switchSceneByGivenPath(event, PagesPaths.LOGIN);
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
        scenesController.switchSceneByGivenPath(event, user, PagesPaths.PLANES_PAGE);
    }
}