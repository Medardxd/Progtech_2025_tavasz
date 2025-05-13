module com.carrental.carrental {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.carrental.carrental to javafx.fxml;
    exports com.carrental.carrental;
}