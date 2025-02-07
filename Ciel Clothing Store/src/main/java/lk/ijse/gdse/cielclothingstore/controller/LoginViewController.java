package lk.ijse.gdse.cielclothingstore.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.cielclothingstore.bo.custom.UserBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.UserBOImpl;
import lk.ijse.gdse.cielclothingstore.dto.UserDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class LoginViewController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private ImageView imgshowPasswordPane;

    @FXML
    private Hyperlink hyperCreateAnAccount;

    @FXML
    private Hyperlink hyperForgetPassword;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtPasswordShow;

    @FXML
    private JFXTextField txtUserName;


    public void initialize() {
        // Add key event listeners for Enter key on txtUserName or txtPassword
        txtUserName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    loginOnAction(null); // Trigger login action when Enter is pressed
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    loginOnAction(null); // Trigger login action when Enter is pressed
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Optionally, handle Enter key on the login button itself
        btnLogin.setOnAction(event -> {
            try {
                loginOnAction(event);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void loginOnAction(ActionEvent event) throws SQLException, IOException {
        String name = txtUserName.getText();
        String password = txtPassword.getText();

        // Create an instance of LoginViewModel
        UserBO userBO = new UserBOImpl();

        ArrayList<UserDto> userDtos = userBO.getCredentials(name, password);

        if (userDtos.isEmpty()) {
            // If no credentials match, show an error
            new Alert(Alert.AlertType.ERROR, "Incorrect username or password").show();
        } else {
            // If credentials match, check the role of the first user
            UserDto userDto = userDtos.get(0);
            String role = userDto.getRole();

            // Load the appropriate dashboard based on the role
            if ("Cashier".equalsIgnoreCase(role)) {
                mainAnchorPane.getChildren().clear();
                mainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/CashierDashBoardView.fxml")));
            } else if ("Manager".equalsIgnoreCase(role)) {
                mainAnchorPane.getChildren().clear();
                mainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/ManagerDashBoardView.fxml")));
            } else {
                // If the role is not recognized, show an error
                new Alert(Alert.AlertType.ERROR, "Role not recognized").show();
            }
        }
    }

    @FXML
    void showPasswordEyeLoginpressed(MouseEvent event) {
        txtPasswordShow.setText(txtPassword.getText());
        if (!txtPassword.getText().isEmpty()) {
            txtPasswordShow.setVisible(true);
            imgshowPasswordPane.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/Eye.png"))));
        } else {
            txtPasswordShow.setVisible(false);
        }
    }

    @FXML
    public void hidePasswordEyeLoginreleased(MouseEvent mouseEvent) {
        txtPassword.setText(txtPasswordShow.getText());
        txtPasswordShow.setVisible(false);
        imgshowPasswordPane.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/closeEyePassword.png"))));
    }

    @FXML
    void fogotPasswordOnAction(ActionEvent event) {
        try {
            mainAnchorPane.getChildren().clear();
            mainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/ForgotPasswordView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openRegistationPage(ActionEvent event) {
        try {
            mainAnchorPane.getChildren().clear();
            Parent root = FXMLLoader.load(getClass().getResource("/view/RegistrationView.fxml"));
            mainAnchorPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Registration Page: " + e.getMessage()).show();
        }
    }
}

