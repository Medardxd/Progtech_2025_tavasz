package com.carrental.auth;

import com.carrental.db.DbUtil;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


public class RegisterController {
    @FXML private TextField tfUser;
    @FXML private PasswordField pfPass;
    @FXML private Label lblMsg;


    @FXML
    private void onRegister(ActionEvent e) {
        String user = tfUser.getText().trim();
        String pass = pfPass.getText().trim();

        // enforce minimum length
        if (user.length() < 3 || pass.length() < 3) {
            lblMsg.setText("⚠️ Both username and password must be at least 3 characters.");
            return;
        }

        if (DbUtil.register(user, pass)) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/carrental/view/login.fxml"));
                Parent loginRoot = loader.load();

                // Grab the controller and send the “success” message
                LoginController loginCtrl = loader.getController();
                loginCtrl.showMessage("✔ Registration successful, please log in.");

                // Swap the scene
                tfUser.getScene().setRoot(loginRoot);
            } catch (Exception ex) {
                lblMsg.setText("⚠️ Error loading login screen.");
                ex.printStackTrace();
            }
        } else {
            lblMsg.setText("✖ Registration failed (username may exist).");
        }
    }



    @FXML private void backToLogin(ActionEvent e) throws Exception {
        tfUser.getScene().setRoot(
                FXMLLoader.load(getClass().getResource("/com/carrental/view/login.fxml"))
        );
    }
}
