package com.carrental.auth;


import com.carrental.auth.LoggedInUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.*;

public class ReservationController {

    @FXML
    private ListView<Reservation> reservationList;

    private final String DB_URL = "jdbc:sqlite:car-rental.db";
    private final ObservableList<Reservation> reservations = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadReservations();
        reservationList.setItems(reservations);

        reservationList.setCellFactory(listView -> new ListCell<>() {
            private final Label label = new Label();
            private final Button deleteBtn = new Button("🗑 Törlés");
            private final HBox content = new HBox(10, label, deleteBtn);

            {
                deleteBtn.setOnAction(e -> {
                    Reservation item = getItem();
                    if (item != null) {
                        deleteReservation(item.getId());
                        reservations.remove(item);
                    }
                });
            }

            @Override
            protected void updateItem(Reservation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item.toString());
                    setGraphic(content);
                }
            }
        });
    }

    private void loadReservations() {
        reservations.clear();
        int userId = LoggedInUser.getUserId();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement("""
                 SELECT r.id, r.startDate, r.endDate, c.brand, c.model, c.licensePlate, r.fullPrice
                 FROM rentals r
                 JOIN cars c ON r.carID = c.carID
                 WHERE r.userID = ?
             """)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation res = new Reservation(
                        rs.getInt("id"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("licensePlate"),
                        rs.getDouble("fullPrice")
                );
                reservations.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteReservation(int reservationId) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement("DELETE FROM rentals WHERE id = ?")) {
            ps.setInt(1, reservationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    // Segédosztály a foglalásokhoz
    public static class Reservation {
        private final int id;
        private final String start, end, brand, model, plate;
        private final double price;

        public Reservation(int id, String start, String end, String brand, String model, String plate, double price) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.brand = brand;
            this.model = model;
            this.plate = plate;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return String.format("🚗 %s %s (%s)\n📅 %s - %s\n💰 %.2f Ft",
                    brand, model, plate, start, end, price);
        }
    }
}