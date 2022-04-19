package controllers;

import constants.PagesPaths;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ScenesController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchSceneToLoginPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource(PagesPaths.LOGIN));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchSceneToRegistrationPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource(PagesPaths.REGISTRATION));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchSceneToSchedulePage(ActionEvent event, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PagesPaths.SCHEDULE_PAGE));
        root = loader.load();
        MainPageControllers mainPageControllers = loader.getController();
        mainPageControllers.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void switchSceneToUsersPage(ActionEvent event, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PagesPaths.USERS_PAGE));
        root = loader.load();
        UsersController usersController = loader.getController();
        usersController.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public void switchSceneToPlanesPage(ActionEvent event, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PagesPaths.PLANES_PAGE));
        root = loader.load();
        PlanesController planesController = loader.getController();
        planesController.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public void switchSceneToMyOrdersPage(ActionEvent event, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PagesPaths.MY_ORDERS_PAGE));
        root = loader.load();
        MyOrdersController myOrdersController = loader.getController();
        myOrdersController.setUser(user);
        myOrdersController.setUpMyOrdersTable();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}
