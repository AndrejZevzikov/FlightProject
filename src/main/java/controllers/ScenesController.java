package controllers;

import constants.Pages;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;

public class ScenesController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void changeSceneByGivenPageNonReg(ActionEvent event, String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(page));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void changeSceneToMainPage(ActionEvent event, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Pages.MAIN_PAGE));
        root = loader.load();
        MainPageControllers mainPageControllers = loader.getController();
        mainPageControllers.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeSceneToUsersPage(ActionEvent event, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Pages.USERS_PAGE));
        root = loader.load();
        UsersController usersController = loader.getController();
        usersController.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public void changeSceneToPlanesPage(ActionEvent event, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Pages.PLANES_PAGE));
        root = loader.load();
        PlanesController planesController = loader.getController();
        planesController.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public void changeSceneToMyOrdersPage(ActionEvent event, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Pages.MY_ORDERS_PAGE));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(true);
        stage.setScene(scene);

        MyOrdersController myOrdersController = loader.getController();
        myOrdersController.setUser(user);
        myOrdersController.setUpMyOrdersTable();
        stage.show();
    }
}
