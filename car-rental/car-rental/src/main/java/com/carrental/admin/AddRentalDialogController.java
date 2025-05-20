package com.carrental.admin;

import com.carrental.db.DbUtil;
import com.carrental.model.*;
import com.carrental.patterns.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AddRentalDialogController {

    @FXML private TextField tfUserId;
    @FXML private ComboBox<Car> cbCar;
    @FXML private DatePicker dpStart, dpEnd;
    @FXML private CheckBox  chkGPS, chkIns;

    private static final String DB = DbUtil.URL;

    @FXML public void initialize() {
        // load all cars for combo
        try(Connection c=DriverManager.getConnection(DB);
            Statement st=c.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM cars")){
            while(rs.next()){
                CarType type = "PREMIUM".equalsIgnoreCase(rs.getString("type"))
                        ? CarType.PREMIUM : CarType.STANDARD;
                cbCar.getItems().add(
                        new Car(rs.getInt("carID"),
                                rs.getString("brand")+' '+rs.getString("model"),
                                type,
                                rs.getDouble("pricePerDay")*7)
                );
            }
        }catch(Exception e){e.printStackTrace();}
    }

    @FXML private void onAdd() {
        try {
            int userId = Integer.parseInt(tfUserId.getText().trim());
            Car car    = cbCar.getValue();
            LocalDate s= dpStart.getValue(), e=dpEnd.getValue();
            if(car==null||s==null||e==null||e.isBefore(s)){throw new IllegalArgumentException("Bad data");}

            int weeks = (int)Math.ceil((ChronoUnit.DAYS.between(s,e)+1)/7.0);
            PricingStrategy strat = (car.getType()==CarType.PREMIUM)? new PremiumStrategy()
                    : new StandardStrategy();
            Rental r = new BasicRental(car, null, weeks, strat);
            if(chkGPS.isSelected())  r = new GPS(r);
            if(chkIns.isSelected())  r = new Insurance(r);

            try(Connection c = DriverManager.getConnection(DB);
                PreparedStatement ps = c.prepareStatement("""
                     INSERT INTO rentals(startDate,endDate,carID,userID,fullPrice)
                     VALUES(?,?,?,?,?)
                """)){
                ps.setString(1,s.toString());
                ps.setString(2,e.toString());
                ps.setInt   (3,car.id());
                ps.setInt   (4,userId);
                ps.setDouble(5,r.cost());
                ps.executeUpdate();
            }
        }catch(Exception ex){
            new Alert(Alert.AlertType.ERROR,"Insert failed: "+ex.getMessage()).showAndWait();
            return;
        }
        close();
    }
    @FXML private void onCancel(){ close(); }
    private void close(){ ((Stage) tfUserId.getScene().getWindow()).close(); }
}
