package com.carrental.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private VBox carListContainer;

    private static final String DB_URL = "jdbc:sqlite:car-rental.db";

    private int loggedInUserId;

    public void setLoggedInUserId(int userId) {
        this.loggedInUserId = userId;
    }
    @FXML
    private void onLogout(ActionEvent e) {
        try {
            Parent loginRoot = FXMLLoader.load(
                    getClass().getResource("/com/carrental/view/login.fxml"));
            // swap back to login
            ((javafx.scene.Node)e.getSource())
                    .getScene()
                    .setRoot(loginRoot);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
