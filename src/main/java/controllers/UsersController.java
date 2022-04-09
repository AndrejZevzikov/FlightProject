package controllers;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UsersController {
    @FXML
    public Label userLabel;
    private User user;

    public void setUser(User user) {
        this.user = user;
        userLabel.setText("Hi, " + user.getUserName());
    }
}
