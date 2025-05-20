package com.carrental.auth;

import com.carrental.db.DbUtil;
import com.carrental.model.*;
import com.carrental.patterns.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MainMenuController {

    @FXML private VBox   carListContainer;

    // admin buttons declared in FXML
    @FXML private Button addCarButton;
    @FXML private Button addRentalButton;
    @FXML private Button reportButton;

    private static final String DB_URL = DbUtil.URL;

    /* ------------------------------------------------------------------ */
    @FXML
    public void initialize() {

        // hide admin bar for normal users
        if (!LoggedInUser.isAdmin() && addCarButton != null) {
            addCarButton.setManaged(false); addCarButton.setVisible(false);
            addRentalButton.setManaged(false); addRentalButton.setVisible(false);
            reportButton.setManaged(false);   reportButton.setVisible(false);
        }
        loadCars();
    }

    /* ------------------------------------------------------------------ */
    /*  build dynamic list of cars                                        */
    /* ------------------------------------------------------------------ */
    /** Build the “Available Cars” list */
    private void loadCars() {
        carListContainer.getChildren().clear();

        String sql = "SELECT * FROM cars";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement  stmt = conn.createStatement();
             ResultSet  rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {

                /* ------------- map DB row -> Car domain object ---------- */
                double pricePerDay = rs.getDouble("pricePerDay");      // read ONCE
                CarType type = "PREMIUM".equalsIgnoreCase(rs.getString("type"))
                        ? CarType.PREMIUM : CarType.STANDARD;

                Car car = new Car(
                        rs.getInt("carID"),
                        rs.getString("brand") + ' ' + rs.getString("model"),
                        type,
                        pricePerDay * 7                  // daily → weekly
                );

                /* ------------- UI block for one car -------------------- */
                VBox carBox = new VBox(5);
                carBox.setStyle("-fx-border-color: gray; -fx-padding: 10;");

                Label info = new Label(car.getModel() + "  $" + pricePerDay + "/day");

                DatePicker startPicker = new DatePicker();
                DatePicker endPicker   = new DatePicker();
                Label priceLabel       = new Label("Total: $0.00");
                Button reserveBtn      = new Button("Reserve");
                reserveBtn.setDisable(true);

                // listeners now use the local pricePerDay variable
                startPicker.valueProperty().addListener((obs,o,n) ->
                        updateTotal(startPicker,endPicker,pricePerDay,priceLabel,reserveBtn));
                endPicker.valueProperty().addListener((obs,o,n) ->
                        updateTotal(startPicker,endPicker,pricePerDay,priceLabel,reserveBtn));

                reserveBtn.setOnAction(ev -> {
                    LocalDate s = startPicker.getValue();
                    LocalDate e = endPicker.getValue();
                    if (s != null && e != null && !e.isBefore(s)) {
                        int weeks = (int) Math.ceil((ChronoUnit.DAYS.between(s, e) + 1) / 7.0);

                        /* -------- Strategy chooses price rule -------- */
                        PricingStrategy strat = (car.getType() == CarType.PREMIUM)
                                ? new PremiumStrategy()
                                : new StandardStrategy();

                        /* -------- Base rental ------------------------ */
                        Rental r = new BasicRental(car, LoggedInUser.get(), weeks, strat);

                        /* -------- Decorators (always GPS for demo) --- */
                        r = new GPS(r);

                        double total = r.cost();
                        reserveToDB(car.id(), s, e, total);

                        new Alert(Alert.AlertType.INFORMATION,
                                "Reserved!\n" + r.details() + "\nCost $" + total)
                                .showAndWait();
                    }
                });

                carBox.getChildren().addAll(
                        info,
                        new Label("Start:"), startPicker,
                        new Label("End:"),   endPicker,
                        priceLabel, reserveBtn);

                carListContainer.getChildren().add(carBox);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    /* helper used in price-preview pop-up */
    private void updateTotal(DatePicker start, DatePicker end,
                             double daily, Label label, Button btn) {
        LocalDate s = start.getValue(), e = end.getValue();
        if(s!=null && e!=null && !e.isBefore(s)){
            long days = ChronoUnit.DAYS.between(s,e)+1;
            label.setText(String.format("Total: $%.2f", days*daily));
            btn.setDisable(false);
        }else{
            label.setText("Total: $0.00"); btn.setDisable(true);
        }
    }

    /* ------------------------------------------------------------------ */
    /* Save reservation to DB                                             */
    /* ------------------------------------------------------------------ */
    private void reserveToDB(int carID, LocalDate start, LocalDate end, double price){
        String sql = "INSERT INTO rentals(startDate,endDate,carID,userID,fullPrice) VALUES(?,?,?,?,?)";
        try(Connection c=DriverManager.getConnection(DB_URL);
            PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,start.toString());
            ps.setString(2,end.toString());
            ps.setInt(3,carID);
            ps.setInt(4,LoggedInUser.id());
            ps.setDouble(5,price);
            ps.executeUpdate();
        }catch(Exception ex){ ex.printStackTrace(); }
    }

    /* ------------------------------------------------------------------ */
    /* ---- navigation + admin stubs ------------------------------------ */
    /* ------------------------------------------------------------------ */
    @FXML private void openReservations(ActionEvent e){ change(e,"/com/carrental/view/reservation-view.fxml"); }
    @FXML private void onLogout(ActionEvent e){ LoggedInUser.set(null); change(e,"/com/carrental/view/login.fxml"); }

    @FXML private void onAddCar(ActionEvent e){
        openDialog("/com/carrental/view/add-car-dialog.fxml", "Add Car");
        loadCars();               // refresh list afterwards
    }
    @FXML private void onAddRental(ActionEvent e){
        openDialog("/com/carrental/view/add-rental-dialog.fxml", "Add Rental");
    }

    @FXML private void onReport(ActionEvent e){
        SummaryVisitor vis = new SummaryVisitor();
        // simplistic example: read all rentals from DB and visit each
        String sql="SELECT r.*,c.model,c.brand FROM rentals r JOIN cars c ON r.carID=c.carID";
        try(Connection c=DriverManager.getConnection(DB_URL);
            Statement st=c.createStatement();
            ResultSet rs=st.executeQuery(sql)){
            while(rs.next()){
                CarType type = CarType.valueOf(rs.getString("type"));
                Car car = new Car(rs.getInt("carID"),
                        rs.getString("brand")+" "+rs.getString("model"),
                        type,
                        0);
                User user = new User(rs.getInt("userID"),"[id:"+rs.getInt("userID")+']',false);
                Rental r = new BasicRental(car,user,1,new StandardStrategy());
                r.accept(vis);
            }
        }catch(Exception ex){ex.printStackTrace();}
        System.out.println("--- REPORT ---\n"+vis.report());
    }

    private void change(ActionEvent e,String f){
        try{ Parent r = FXMLLoader.load(getClass().getResource(f));
            ((javafx.scene.Node)e.getSource()).getScene().setRoot(r);
        }catch(IOException ex){ex.printStackTrace();}
    }

    private void openDialog(String fxml, String title){
        try{
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage  dlg  = new Stage();
            dlg.initModality(Modality.APPLICATION_MODAL);
            dlg.setTitle(title);
            dlg.setScene(new javafx.scene.Scene(root));
            dlg.showAndWait();
        }catch(Exception ex){ ex.printStackTrace(); }
    }
}
