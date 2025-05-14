package com.carrental.auth;

import com.carrental.db.DbUtil;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class LoginController {
    @FXML private TextField tfUser;
    @FXML private PasswordField pfPass;
    @FXML private Label lblMsg;

    // Called by RegisterController after a successful sign-up
    public void showMessage(String msg) {
        lblMsg.setText(msg);
    }

    @FXML
    private void onLogin(ActionEvent e) {
        if (DbUtil.checkCredentials(tfUser.getText(), pfPass.getText())) {
            try {
                Parent mainRoot = FXMLLoader.load(
                        getClass().getResource("/com/carrental/view/main-menu.fxml")
                );
                tfUser.getScene().setRoot(mainRoot);
            } catch (IOException ex) {
                lblMsg.setText("⚠️ Could not load main menu.");
                ex.printStackTrace();
            }
        } else {
            lblMsg.setText("✖ Invalid user or password");
        }
    }

    @FXML private void openRegister(ActionEvent e) throws Exception {
        tfUser.getScene().setRoot(
                FXMLLoader.load(getClass().getResource("/com/carrental/view/register.fxml"))
        );
    }
}
