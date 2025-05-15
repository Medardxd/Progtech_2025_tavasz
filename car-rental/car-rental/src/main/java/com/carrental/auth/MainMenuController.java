package com.carrental.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private VBox carListContainer;

    private static final String DB_URL = "jdbc:sqlite:car-rental.db";

    private int loggedInUserId;

    public void setLoggedInUserId(int userId) {
        this.loggedInUserId = userId;
    }
    private void loadCars() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM cars")) {

            while (rs.next()) {
                int carID = rs.getInt("carID");
                String licensePlate = rs.getString("licensePlate");
                double pricePerDay = rs.getDouble("pricePerDay");
                String brand = rs.getString("brand");
                int year = rs.getInt("yearOfCreation");

                VBox carBox = new VBox(5);
                carBox.setStyle("-fx-border-color: gray; -fx-padding: 10;");

                Label info = new Label("Brand: " + brand +
                        ", Plate: " + licensePlate +
                        ", Year: " + year +
                        ", Price/Day: $" + pricePerDay);

                DatePicker startPicker = new DatePicker();
                DatePicker endPicker = new DatePicker();
                Label priceLabel = new Label("Total: $0.00");
                Button reserveBtn = new Button("Reserve");
                reserveBtn.setDisable(true);

                startPicker.valueProperty().addListener((obs, o, n) ->
                        updateTotal(startPicker, endPicker, pricePerDay, priceLabel, reserveBtn));
                endPicker.valueProperty().addListener((obs, o, n) ->
                        updateTotal(startPicker, endPicker, pricePerDay, priceLabel, reserveBtn));

                reserveBtn.setOnAction(e -> {
                    LocalDate start = startPicker.getValue();
                    LocalDate end = endPicker.getValue();
                    if (start != null && end != null && !end.isBefore(start)) {
                        double total = (ChronoUnit.DAYS.between(start, end) + 1) * pricePerDay;
                        reserve(carID, start, end, total);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reserved successfully!");
                        alert.showAndWait();
                    }
                });

                carBox.getChildren().addAll(info,
                        new Label("Start date:"), startPicker,
                        new Label("End date:"), endPicker,
                        priceLabel, reserveBtn);

                carListContainer.getChildren().add(carBox);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
