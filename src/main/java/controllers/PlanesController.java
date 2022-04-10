package controllers;

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
import services.PlaneValidationService;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class PlanesController implements Initializable {

    @FXML
    public Label errorLabel;
    @FXML
    public Button scheduleButton;
    @FXML
    public Button usersButton;
    @FXML
    public TableView<Plane> planeTable;
    @FXML
    public TableColumn<Plane, Long> idColumn;
    @FXML
    public TableColumn<Plane, String> companyNameColumn;
    @FXML
    public TableColumn<Plane, String> planeNumberColumn;
    @FXML
    public TableColumn<Plane, Integer> capacityColumn;
    @FXML
    public Button deleteSelected;
    @FXML
    public Button deleteByIdButton;
    @FXML
    public TextField deleteByIdTextField;
    @FXML
    public Button addButton;
    @FXML
    public TextField insertCompanyTextField;
    @FXML
    public TextField insertCapacityTextField;
    @FXML
    public TextField insertNumberTextField;
    @FXML
    public Button deleteSelectedPlane;

    private ScenesController scenesController = new ScenesController();
    private PlaneRepository planeRepository = new PlaneRepository();
    private PlaneValidationService planeValidationService = new PlaneValidationService();
    private User user;
    ObservableList<Plane> planes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        planes = FXCollections.observableArrayList(planeRepository.findAll());
        idColumn.setCellValueFactory(new PropertyValueFactory<Plane, Long>("id"));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<Plane, String>("companyName"));
        planeNumberColumn.setCellValueFactory(new PropertyValueFactory<Plane, String>("number"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<Plane, Integer>("capacity"));
        planeTable.setItems(planes);
    }

    public void deleteSelectedPlane(ActionEvent event) throws IOException {
        planeRepository.delete(planeTable.getSelectionModel().getSelectedItem());
        scenesController.changeSceneToPlanesPage(event, user);
    }

    public void onAddButton(ActionEvent event) throws IOException {
        if (planeValidationService.isPlaneValid(createPlaneFromInput(),errorLabel)){
            saveAndRefresh(event);
        }
    }

    public void deletePlaneById(ActionEvent event) throws IOException {
        planeRepository.deleteById(Long.parseLong(deleteByIdTextField.getText()));
        scenesController.changeSceneToPlanesPage(event, user);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void onScheduleButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToMainPage(event, user);
    }

    public void onUsersButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToUsersPage(event, user);
    }

    private Plane createPlaneFromInput(){
        return Plane.builder()
                .companyName(insertCompanyTextField.getText())
                .capacity(Integer.parseInt(insertCapacityTextField.getText()))
                .number(insertNumberTextField.getText())
                .build();
    }

    private void saveAndRefresh(ActionEvent event) throws IOException {
        planeRepository.saveOrUpdate(createPlaneFromInput());
        scenesController.changeSceneToPlanesPage(event,user);
    }

    public void onMyOrdersButton(ActionEvent event) throws IOException {
        scenesController.changeSceneToMyOrdersPage(event,user);
    }
}