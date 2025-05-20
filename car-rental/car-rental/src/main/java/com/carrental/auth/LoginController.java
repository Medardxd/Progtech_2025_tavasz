package com.carrental.auth;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.*;

public class LoginController {
    @FXML private TextField tfUser;
    @FXML private PasswordField pfPass;
    @FXML private Label lblMsg;


    public void showMessage(String msg) {
        lblMsg.setText(msg);
    }


    @FXML private void onLogin(ActionEvent e){
        User u = AuthService.login(tfUser.getText().trim(),pfPass.getText());
        if(u==null){ lblMsg.setText("❌ Wrong credentials"); return; }
        LoggedInUser.set(u);
        change("/com/carrental/view/main-menu.fxml");
    }
    @FXML private void openRegister(ActionEvent e){
        change("/com/carrental/view/register.fxml");
    }
    private void change(String f){
        try{ Parent r = FXMLLoader.load(getClass().getResource(f));
            tfUser.getScene().setRoot(r);
        }catch(Exception ex){ex.printStackTrace();}
    }
}
