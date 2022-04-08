package controllers;

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


    public void changeSceneByGivenPageNonReg(ActionEvent event, String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(page));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void changeSceneToMainPage(ActionEvent event, String page, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
        root = loader.load();
        MainPageControllers mainPageControllers = loader.getController();
        mainPageControllers.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }





}
