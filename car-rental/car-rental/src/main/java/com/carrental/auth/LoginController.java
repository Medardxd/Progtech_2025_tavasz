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

    public void showMessage(String msg) {
        lblMsg.setText(msg);
    }

    @FXML
    private void onLogin(ActionEvent e) {
        String username = tfUser.getText();
        String password = pfPass.getText();

        if (DbUtil.checkCredentials(username, password)) {
            try {
                int userId = DbUtil.getUserIdByUsername(username);
                LoggedInUser.setUserId(userId); // itt beállítjuk

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

    @FXML
    private void openRegister(ActionEvent e) throws Exception {
        tfUser.getScene().setRoot(
                FXMLLoader.load(getClass().getResource("/com/carrental/view/register.fxml"))
        );
    }
}