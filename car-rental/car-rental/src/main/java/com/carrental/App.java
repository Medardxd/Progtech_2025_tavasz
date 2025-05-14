package com.carrental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(
                FXMLLoader.load(getClass().getResource("/com/carrental/view/login.fxml"))
        ));
        stage.setTitle("Car-Rental • Login");
        stage.show();
    }
    public static void main(String[] args) { launch(); }
}
