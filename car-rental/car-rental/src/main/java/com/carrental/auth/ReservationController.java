package com.carrental.auth;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationController {

    @FXML
    private ListView<String> reservationList;

    private final String DB_URL = "jdbc:sqlite:car-rental.db";

    @FXML
    public void initialize() {
        int userId = LoggedInUser.getUserId();
        List<String> reservations = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement("""
                 SELECT r.startDate, r.endDate, c.brand, c.model, c.licensePlate, r.fullPrice
                 FROM rentals r
                 JOIN cars c ON r.carID = c.carID
                 WHERE r.userID = ?
             """)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String line = String.format("🚗 %s %s (%s)\n📅 %s - %s\n💰 %.2f Ft",
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("licensePlate"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getDouble("fullPrice"));
                reservations.add(line);
            }

        } catch (SQLException e) {
            reservations.add("⚠️ Hiba a foglalások betöltésekor.");
            e.printStackTrace();
        }

        reservationList.setItems(FXCollections.observableArrayList(reservations));
    }

    @FXML
    private void onBack() {
        try {
            Parent mainRoot = FXMLLoader.load(getClass().getResource("/com/carrental/view/main-menu.fxml"));
            reservationList.getScene().setRoot(mainRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
