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

    /* ---------- FXML –– public widgets -------------------------------- */
    @FXML
    VBox   carListContainer;
    @FXML
    Button addCarButton;
    @FXML
    Button addRentalButton;
    @FXML
    Button reportButton;
    @FXML
    Button btnMyRentals;

    /* ---------- JDBC --------------------------------------------------- */
    private static final String DB_URL = DbUtil.URL;

    /* =================================================================== */
    /*  INITIALISE VIEW                                                    */
    /* =================================================================== */
    @FXML
    public void initialize() {

        /* hide “My rentals” for admin, admin-bar for normal users */
        if (LoggedInUser.isAdmin()) {
            btnMyRentals.setManaged(false); btnMyRentals.setVisible(false);
        } else {
            addCarButton.setManaged(false);  addCarButton.setVisible(false);
            addRentalButton.setManaged(false); addRentalButton.setVisible(false);
            reportButton.setManaged(false);  reportButton.setVisible(false);
        }

        loadCars();
    }

    /* =================================================================== */
    /*  BUILD “AVAILABLE CARS” LIST                                        */
    /* =================================================================== */
    private void loadCars() {
        carListContainer.getChildren().clear();

        final String sql = "SELECT * FROM cars";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement  st   = conn.createStatement();
             ResultSet  rs   = st.executeQuery(sql)) {

            while (rs.next()) {

                /* ---- Map DB row → Car -------------------------------- */
                CarType type = "PREMIUM".equalsIgnoreCase(rs.getString("type"))
                        ? CarType.PREMIUM : CarType.STANDARD;

                double pricePerDay = rs.getDouble("pricePerDay");

                Car car = new Car(
                        rs.getInt("carID"),
                        rs.getString("brand") + ' ' + rs.getString("model"),
                        type,
                        pricePerDay
                );

                /* ---- UI block ---------------------------------------- */
                VBox box = new VBox(5);
                box.setStyle("-fx-border-color: gray; -fx-padding: 10;");

                /*    headline shows price AND the car type (★=premium, •=standard)   */
                String tag = (car.getType() == CarType.PREMIUM) ? "★" : "•";
                Label info = new Label(tag + " " + car.getModel() + "  $" + pricePerDay + "/day");

                /*    admin-only delete button                                        */
                Button btnDelete = new Button("Delete");
                btnDelete.setOnAction(e -> {
                    if (new Alert(Alert.AlertType.CONFIRMATION,
                            "Delete " + car.getModel() + " ?").showAndWait()
                            .orElse(ButtonType.CANCEL) == ButtonType.OK) {

                        try (Connection c = DriverManager.getConnection(DB_URL);
                             PreparedStatement ps = c.prepareStatement(
                                     "DELETE FROM cars WHERE carID = ?")) {
                            ps.setInt(1, car.id());
                            ps.executeUpdate();
                        } catch (SQLException ex) { ex.printStackTrace(); }

                        loadCars();           // refresh list after deletion
                    }
                });
                boolean admin = LoggedInUser.isAdmin();
                btnDelete.setVisible(admin);
                btnDelete.setManaged(admin);   // so layout collapses for users

                /* ➌   normal rental controls                                         */
                DatePicker dpStart = new DatePicker();
                DatePicker dpEnd   = new DatePicker();

                CheckBox  chkGps   = new CheckBox("GPS (+$10)");
                CheckBox  chkIns   = new CheckBox("Insurance (+$15)");

                Label  lblTotal    = new Label("Total: $0.00");
                Button btnReserve  = new Button("Reserve");
                btnReserve.setDisable(true);

                /* ---- listeners that update price / enable reserve button ---------- */
                dpStart.valueProperty().addListener((o,ov,nv) ->
                        updateTotal(dpStart, dpEnd, pricePerDay,
                                lblTotal, btnReserve, chkGps, chkIns));
                dpEnd.valueProperty().addListener((o,ov,nv) ->
                        updateTotal(dpStart, dpEnd, pricePerDay,
                                lblTotal, btnReserve, chkGps, chkIns));
                chkGps.selectedProperty().addListener((o,ov,nv) ->
                        updateTotal(dpStart, dpEnd, pricePerDay,
                                lblTotal, btnReserve, chkGps, chkIns));
                chkIns.selectedProperty().addListener((o,ov,nv) ->
                        updateTotal(dpStart, dpEnd, pricePerDay,
                                lblTotal, btnReserve, chkGps, chkIns));

                /* booking --------------------------------------------- */
                btnReserve.setOnAction(ev -> {
                    LocalDate s = dpStart.getValue();
                    LocalDate e = dpEnd.getValue();
                    if (s == null || e == null || e.isBefore(s)) return;

                    long days  = ChronoUnit.DAYS.between(s,e) + 1;

                    /* strategy */
                    PricingStrategy strat = (car.getType()==CarType.PREMIUM)
                            ? new PremiumStrategy()
                            : new StandardStrategy();

                    Rental r = new BasicRental(car, LoggedInUser.get(), s, e, strat);

                    if (chkGps.isSelected()) r = new GPS(r);
                    if (chkIns.isSelected()) r = new Insurance(r);

                    double cost = r.cost();
                    try {
                        reserveToDB(car.id(), s, e, cost);      // may throw SQLException
                    } catch (SQLException ex) {
                        new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
                        return;                                 // abort the handler
                    }

                    new Alert(Alert.AlertType.INFORMATION,
                            "Reserved!\n"+r.details()+"\nCost $"+cost).showAndWait();
                });

                box.getChildren().addAll(
                        info,
                        btnDelete,
                        new Label("Start:"), dpStart,
                        new Label("End:"),   dpEnd,
                        chkGps, chkIns,
                        lblTotal, btnReserve
                );

                carListContainer.getChildren().add(box);
            }

        } catch (SQLException ex) { ex.printStackTrace(); }
    }
    public double calculateTotal(LocalDate startDate, LocalDate endDate, double dailyPrice, boolean gps, boolean insurance) {
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            return 0.0;
        }

        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        double total = days * dailyPrice;
        if (gps) total += 10;
        if (insurance) total += 15;
        return total;
    }
    /* ---------- helper: live total ------------------------------------ */
    void updateTotal(DatePicker s, DatePicker e,
                     double daily,
                     Label lbl, Button btn,
                     CheckBox gps, CheckBox ins) {

        LocalDate a=s.getValue(), b=e.getValue();
        if (a!=null && b!=null && !b.isBefore(a)) {
            long days = ChronoUnit.DAYS.between(a,b)+1;
            double tot = days*daily;
            if(gps.isSelected()) tot+=10;
            if(ins.isSelected()) tot+=15;

            lbl.setText(String.format("Total: $%.2f",tot));
            btn.setDisable(false);
        } else {
            lbl.setText("Total: $0.00"); btn.setDisable(true);
        }
    }

    /* ---------- DB insert --------------------------------------------- */
    private void reserveToDB(int carID, LocalDate s, LocalDate e, double price)
            throws SQLException {

        try (Connection c = DriverManager.getConnection(DB_URL)) {

            /* 1 ► collision check */
            String clash = """
            SELECT 1 FROM rentals
            WHERE carID = ?
              AND NOT(endDate  < ? OR startDate > ?)
            LIMIT 1
        """;
            try (PreparedStatement ps = c.prepareStatement(clash)) {
                ps.setInt   (1, carID);
                ps.setString(2, s.toString());   // wanted interval: s … e
                ps.setString(3, e.toString());
                if (ps.executeQuery().next()) {
                    throw new SQLException("Car already booked for this period!");
                }
            }

            /* 2 ► real insert */
            String ins = """
            INSERT INTO rentals(startDate,endDate,carID,userID,fullPrice)
            VALUES(?,?,?,?,?)
        """;
            try (PreparedStatement ps = c.prepareStatement(ins)) {
                ps.setString(1, s.toString());
                ps.setString(2, e.toString());
                ps.setInt   (3, carID);
                ps.setInt   (4, LoggedInUser.id());
                ps.setDouble(5, price);
                ps.executeUpdate();
            }
        }
    }


    /* =================================================================== */
    /*  NAVIGATION & ADMIN DIALOGS                                         */
    /* =================================================================== */
    @FXML private void openReservations(ActionEvent e){ swap(e,"/com/carrental/view/reservation-view.fxml"); }
    @FXML private void onLogout(ActionEvent e){ LoggedInUser.set(null); swap(e,"/com/carrental/view/login.fxml"); }

    @FXML private void onAddCar   (ActionEvent e){ dialog("/com/carrental/view/add-car-dialog.fxml",   "Add Car");    loadCars(); }
    @FXML private void onAddRental(ActionEvent e){ dialog("/com/carrental/view/add-rental-dialog.fxml","Add Rental"); }
    /* =================================================================== */
    /*  ADMIN ─ “REPORT” BUTTON                                            */
    /* =================================================================== */
    @FXML
    private void onReport(ActionEvent e) {

        /* 1 ─ build a visitor that accumulates lines + grand total */
        SummaryVisitor vis = new SummaryVisitor();

        /* 2 ─ query every rental with the data we need              */
        final String sql = """
        SELECT r.startDate, r.endDate, r.fullPrice,
               u.username,
               c.brand||' '||c.model   AS model,
               c.type
        FROM   rentals r
        JOIN   users   u ON u.id    = r.userID
        JOIN   cars    c ON c.carID = r.carID
        ORDER  BY r.startDate
    """;

        try (Connection c = DriverManager.getConnection(DB_URL);
             Statement  st = c.createStatement();
             ResultSet  rs = st.executeQuery(sql)) {

            while (rs.next()) {

                /* Build the free-text line ourselves ------------------------ */
                String line =
                        rs.getString("username") + " renting "
                                + rs.getString("model")   + " "
                                + rs.getString("startDate") + " – "
                                + rs.getString("endDate");

                double paid = rs.getDouble("fullPrice");

                vis.addLine(line, paid);        // <-- use the helper
            }

        } catch (Exception ex) {
            /* if *anything* goes wrong show the error to the admin */
            new Alert(Alert.AlertType.ERROR, "Could not build report:\n" + ex.getMessage()).showAndWait();
            return;
        }

        /* 3 ─ pop the finished report */
        Alert a = new Alert(Alert.AlertType.INFORMATION, vis.report(),
                ButtonType.OK);
        a.setHeaderText("Report");      // title line inside the dialog
        a.showAndWait();
    }


    private void swap(ActionEvent e,String f){
        try{ Parent r=FXMLLoader.load(getClass().getResource(f));
            ((javafx.scene.Node)e.getSource()).getScene().setRoot(r);
        }catch(IOException ex){ ex.printStackTrace(); }
    }
    private void dialog(String fxml,String title){
        try{ Parent root=FXMLLoader.load(getClass().getResource(fxml));
            Stage dlg=new Stage(); dlg.initModality(Modality.APPLICATION_MODAL);
            dlg.setTitle(title); dlg.setScene(new javafx.scene.Scene(root)); dlg.showAndWait();
        }catch(Exception ex){ ex.printStackTrace(); }
    }
}
