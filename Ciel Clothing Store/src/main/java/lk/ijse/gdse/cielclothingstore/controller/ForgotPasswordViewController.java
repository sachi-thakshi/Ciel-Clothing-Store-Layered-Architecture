package lk.ijse.gdse.cielclothingstore.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.cielclothingstore.bo.BOFactory;
import lk.ijse.gdse.cielclothingstore.bo.custom.ForgotPasswordBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.UserBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.ForgotPasswordBOImpl;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.UserBOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ForgotPasswordViewController {
    @FXML
    private AnchorPane FAnchorPane;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private ImageView imgshowPasswordPane;

    @FXML
    private ImageView imgshowPasswordPane1;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtPasswordShow;

    @FXML
    private JFXTextField txtPasswordShow1;

    @FXML
    private JFXTextField txtUsername;

//    private final UserModel userModel = new UserModel();
    private final ForgotPasswordBO forgotPasswordBO = new ForgotPasswordBOImpl();

    @FXML
    void showPasswordEyeLoginpressed1(MouseEvent event) {
        txtPasswordShow.setText(txtPassword.getText());
        if (!txtPassword.getText().isEmpty()) {
            txtPasswordShow.setVisible(true);
            imgshowPasswordPane1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/Eye.png"))));
        } else {
            txtPasswordShow.setVisible(false);
        }
    }

    @FXML
    void hidePasswordEyeLoginreleased1(MouseEvent event) {
        txtPassword.setText(txtPasswordShow.getText());
        txtPasswordShow.setVisible(false);
        imgshowPasswordPane1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/closeEyePassword.png"))));
    }
    @FXML
    void hidePasswordEyeLoginreleased(MouseEvent event) {
        txtConfirmPassword.setText(txtPasswordShow1.getText());
        txtPasswordShow1.setVisible(false);
        imgshowPasswordPane.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/closeEyePassword.png"))));
    }

    @FXML
    void showPasswordEyeLoginpressed(MouseEvent event) {
        txtPasswordShow1.setText(txtConfirmPassword.getText());
        if (!txtConfirmPassword.getText().isEmpty()) {
            txtPasswordShow1.setVisible(true);
            imgshowPasswordPane.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/Eye.png"))));
        } else {
            txtPasswordShow1.setVisible(false);
        }
    }

    @FXML
    void loginOnAction(ActionEvent event) {
        try {
            FAnchorPane.getChildren().clear();
            FAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnResetPasswordOnAction(ActionEvent event) {
        String username = txtUsername.getText();
        String newPassword = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (username.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "All fields are required!").show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
            return;
        }

        try {
            if (forgotPasswordBO.isUsernameExists(username)) {
                boolean isUpdated = forgotPasswordBO.updatePassword(username, newPassword);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Password reset successfully!").show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to reset password!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Username not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while resetting the password!").show();
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtUsername.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
    }

}

