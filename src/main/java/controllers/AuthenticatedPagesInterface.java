package controllers;

import entities.User;
import javafx.event.ActionEvent;

import java.io.IOException;

public interface AuthenticatedPagesInterface {

    void onUsersButton(ActionEvent event) throws IOException;

    void onPlanesButton(ActionEvent event) throws IOException;

    void onScheduleButton(ActionEvent event) throws IOException;

    void onMyOrdersButton(ActionEvent event) throws IOException;

    void onLogoutButton(ActionEvent event) throws IOException;

    void setUser(User user);
}