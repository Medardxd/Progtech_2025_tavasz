module com.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.carrental to javafx.graphics, javafx.fxml;
    opens com.carrental.auth to javafx.fxml;
}
