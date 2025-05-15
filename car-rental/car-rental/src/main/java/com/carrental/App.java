package com.carrental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(
                "/com/carrental/view/login.fxml"));

        Scene scene = new Scene(root, 500, 350);    // width=300, height=250
        scene.getStylesheets().add(
                getClass().getResource("/com/carrental/style/styles.css")
                        .toExternalForm()
        );


        stage.setScene(scene);
        stage.setTitle("Car-Rental");
        stage.setResizable(false);
        stage.show();
    }
}
