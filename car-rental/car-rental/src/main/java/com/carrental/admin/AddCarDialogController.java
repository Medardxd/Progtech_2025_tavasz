package com.carrental.admin;

import com.carrental.db.DbUtil;
import com.carrental.model.CarType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;

public class AddCarDialogController {

    @FXML private TextField tfBrand, tfModel, tfPlate, tfPrice, tfYear;
    @FXML private ComboBox<CarType> cbType;

    private static final String DB = DbUtil.URL;

    @FXML public void initialize() { cbType.getItems().addAll(CarType.values()); }

    @FXML private void onAdd() {
        try (Connection c = DriverManager.getConnection(DB);
             PreparedStatement ps = c.prepareStatement("""
               INSERT INTO cars(brand,model,licensePlate,type,pricePerDay,yearOfCreation)
               VALUES(?,?,?,?,?,?)
            """)) {

            ps.setString(1, tfBrand.getText().trim());
            ps.setString(2, tfModel.getText().trim());
            ps.setString(3, tfPlate.getText().trim());
            ps.setString(4, cbType.getValue().name());
            ps.setDouble(5, Double.parseDouble(tfPrice.getText().trim()));
            ps.setInt   (6, Integer.parseInt(tfYear.getText().trim()));
            ps.executeUpdate();

        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Insert failed: " + ex.getMessage()).showAndWait();
            return;
        }
        close();
    }

    @FXML private void onCancel() { close(); }
    private void close() { ((Stage) tfBrand.getScene().getWindow()).close(); }
}
