module com.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;

    opens com.carrental to javafx.graphics, javafx.fxml;
    opens com.carrental.auth to javafx.fxml;
    opens com.carrental.admin  to javafx.fxml;
}
