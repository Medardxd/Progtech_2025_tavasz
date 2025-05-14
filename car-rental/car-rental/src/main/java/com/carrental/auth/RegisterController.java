package com.carrental.auth;

import com.carrental.db.DbUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

public class RegisterController {
    @FXML private TextField tfUser;
    @FXML private PasswordField pfPass;
    @FXML private Label lblMsg;

    @FXML private void onRegister(ActionEvent e) {
        if (DbUtil.register(tfUser.getText(), pfPass.getText())) {
            lblMsg.setText("✔ Registered! Back to login.");
        } else {
            lblMsg.setText("✖ Registration failed.");
        }
    }

    @FXML private void backToLogin(ActionEvent e) throws Exception {
        tfUser.getScene().setRoot(
                FXMLLoader.load(getClass().getResource("/com/carrental/view/login.fxml"))
        );
    }
}
