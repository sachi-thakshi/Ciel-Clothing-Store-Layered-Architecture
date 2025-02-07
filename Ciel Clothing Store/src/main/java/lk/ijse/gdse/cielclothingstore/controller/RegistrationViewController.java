package lk.ijse.gdse.cielclothingstore.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.cielclothingstore.bo.BOFactory;
import lk.ijse.gdse.cielclothingstore.bo.custom.RegistrationBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.UserBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.RegistrationBOImpl;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.UserBOImpl;
import lk.ijse.gdse.cielclothingstore.dto.RegistrationDto;
import lk.ijse.gdse.cielclothingstore.dto.UserDto;
import lk.ijse.gdse.cielclothingstore.entity.UserAccount;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class RegistrationViewController {

    @FXML
    private Label lblCheckPassword;

    @FXML
    private Label lblUserID;

    @FXML
    private ImageView ImageRegisterPage;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private Hyperlink hyperSignInRegistration;

    @FXML
    private ImageView imgCPasswordPane;

    @FXML
    private ImageView imgEmpoyeeIdPane;

    @FXML
    private ImageView imgNamePane;

    @FXML
    private ImageView imgPasswordEye;

    @FXML
    private ImageView imgPasswordEyeConfirm;

    @FXML
    private ImageView imgPasswordPane;

    @FXML
    private ImageView imgRolePane;

    @FXML
    private ImageView imgUserIdPane;

    @FXML
    private ImageView imgUserNamePane;

    @FXML
    private Label lblAlready;

    @FXML
    private Label lblCPasswordRegistration;

    @FXML
    private Label lblContactoNoRegistration;

    @FXML
    private Label lblEmployeeIdRegistration;

    @FXML
    private Label lblNameRegistration;

    @FXML
    private Label lblPasswordRegistration;

    @FXML
    private Label lblRegister;

    @FXML
    private Label lblRoleRegistration;

    @FXML
    private Label lblUserIdRegistration;

    @FXML
    private Label lblUserNameRegistration;

    @FXML
    private AnchorPane mainAnchorRegistration;

    @FXML
    private Pane pane;

    @FXML
    private TextField txtContactNoRegistration;

    @FXML
    private TextField txtEmployeeIdRegistration;

    @FXML
    private TextField txtNameRegistration;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPasswordRegistration;

    @FXML
    private TextField txtPasswordShow;

    @FXML
    private TextField txtPasswordShowRegistration;

    @FXML
    private TextField txtRoleRegistration;

    @FXML
    private TextField txtUserIdRegistration;

    @FXML
    private TextField txtUserNameRegistration;

//    private final RegistrationModel registrationModel = new RegistrationModel();
    private final RegistrationBO registrationBO = new RegistrationBOImpl();
    UserBO userBO = new UserBOImpl();

    public void initialize() {
        try {
            String nextuserID = registrationBO.getNextUserId();
            lblUserID.setStyle("-fx-text-fill: #0b52ec;");
            lblUserID.setText(nextuserID);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }
    private boolean areFieldsFilled() {

        return  !lblUserID.getText().isEmpty() &&
                !txtUserNameRegistration.getText().isEmpty() &&
                !txtContactNoRegistration.getText().isEmpty() &&
                !txtRoleRegistration.getText().isEmpty() &&
                !txtEmployeeIdRegistration.getText().isEmpty() &&
                !txtRoleRegistration.getText().isEmpty() &&
                !txtPassword.getText().isEmpty() &&
                !txtPassword.getText().isEmpty() &&
                !txtUserNameRegistration.getText().isEmpty();

    }

    @FXML
    void btnopenLoginPageRegistered(ActionEvent event) {
        String userId = lblUserID.getText();
        String name = txtNameRegistration.getText();
        String contactNo = txtContactNoRegistration.getText();
        String role = txtRoleRegistration.getText();
        String employeeId = txtEmployeeIdRegistration.getText();
        String password = txtPasswordRegistration.getText();
        String passwordConfirm = txtPassword.getText();
        String userName = txtUserNameRegistration.getText();

        if(areFieldsFilled()){
            if(password.equals(passwordConfirm)){
                try{
                    UserDto userDto = new UserDto(userId, employeeId, name, role, password, contactNo);
                    boolean isRegistered = userBO.registeredUser(userDto);
                    if (isRegistered) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Congratulations ... Your Registration Is Successful...!");
                        alert.setTitle("Registration Success");
                        alert.setHeaderText(null); // No header
                        alert.showAndWait();
                        refreshPage();

                        //try{Thread.sleep(5);}catch(InterruptedException e){throw new RuntimeException(e);}
                        mainAnchorRegistration.getChildren().clear();
                        mainAnchorRegistration.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginView.fxml")));
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed Your Registration ... Please Try Again ... !").show();
                    }
                } catch (IOException e ) {
                    new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
                } catch (SQLException e1) {
                    new Alert(Alert.AlertType.ERROR,"Fail to load Page Sql Error..! Check Your Employee Id").show();
                }
            }else{
                lblCheckPassword.setText("Check Password In Here ... !");
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Please enter all fields!").show();
        }
    }
    public void refreshPage() {
        txtUserNameRegistration.setText("");
        txtContactNoRegistration.setText("");
        txtRoleRegistration.setText("");
        txtEmployeeIdRegistration.setText("");
        txtRoleRegistration.setText("");
        txtPassword.setText("");
        txtPassword.setText("");
        txtUserNameRegistration.setText("");

    }


    @FXML
    void hidePasswordEyeConfirmLoginreleased(MouseEvent event) {
        txtPassword.setText(txtPasswordShow.getText());
        txtPasswordShow.setVisible(false);
        imgPasswordEyeConfirm.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/closeEyePassword.png"))));

    }

    @FXML
    void showPasswordEyeConfirmLoginpressed(MouseEvent event) {
        txtPasswordShow.setText(txtPassword.getText());
        if (!txtPassword.getText().isEmpty()) {
            txtPasswordShow.setVisible(true);
            imgPasswordEyeConfirm.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/Eye.png"))));
        } else {
            txtPasswordShow.setVisible(false);
        }
    }

    @FXML
    void lblopenLoginPage(MouseEvent event) {
        try {
            mainAnchorRegistration.getChildren().clear();
            mainAnchorRegistration.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void hidePasswordEyeLoginreleased(MouseEvent event) {
        txtPasswordRegistration.setText(txtPasswordShowRegistration.getText());
        txtPasswordShowRegistration.setVisible(false);
        imgPasswordEye.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/closeEyePassword.png"))));
    }

    @FXML
    void showPasswordEyeLoginpressed(MouseEvent event) {
        txtPasswordShowRegistration.setText(txtPasswordRegistration.getText());
        if (!txtPasswordRegistration.getText().isEmpty()) {
            txtPasswordShowRegistration.setVisible(true);
            imgPasswordEye.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/Eye.png"))));
        } else {
            txtPasswordShowRegistration.setVisible(false);
        }
    }
}

