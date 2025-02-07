package lk.ijse.gdse.cielclothingstore.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.cielclothingstore.bo.BOFactory;
import lk.ijse.gdse.cielclothingstore.bo.custom.UserBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.UserBOImpl;
import lk.ijse.gdse.cielclothingstore.dto.UserDto;
import lk.ijse.gdse.cielclothingstore.view.tm.UserTM;

//import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<UserTM, String> colContactNo;

    @FXML
    private TableColumn<UserTM, String> colEmployeeID;

    @FXML
    private TableColumn<UserTM, String> colName;

    @FXML
    private TableColumn<UserTM, String> colPassword;

    @FXML
    private TableColumn<UserTM, String> colRole;

    @FXML
    private TableColumn<UserTM, String> colUserID;

    @FXML
    private Label lblUserId;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtNme;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtContactNo;

//    UserModel userModel = new UserModel();
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USERACCOUNT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colUserID.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));

        try {
            refreshPage();
            String nextUserID = userBO.getNextUserId();
            System.out.println(nextUserID);
            lblUserId.setText(nextUserID);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {

        refreshTable();

        String nextUserID = userBO.getNextUserId();
        lblUserId.setText(nextUserID);

        txtEmployeeId.setText("");
        txtNme.setText("");
        txtRole.setText("");
        txtPassword.setText("");
        txtContactNo.setText("");

        btnDelete.setDisable(true);
        btnUpdate.setDisable(false);
    }

    private void refreshTable() throws SQLException {
        ArrayList<UserDto> userDtos = userBO.getAllUsers();
        ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

        for (UserDto userDto : userDtos) {
            UserTM userTM = new UserTM(
                    userDto.getUserID(),
                    userDto.getEmployeeID(),
                    userDto.getName(),
                    userDto.getRole(),
                    userDto.getPassword(),
                    userDto.getContactNo()
            );
            userTMS.add(userTM);
        }

        tblUser.setItems(userTMS);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String UserID = lblUserId.getText();
        String EmployeeID = txtEmployeeId.getText();
        String name = txtNme.getText();
        String role = txtRole.getText();
        String password = txtPassword.getText();
        String contactNo = txtContactNo.getText();

        String contactNoPattern = "^\\d{3}-\\d{7}$";

        boolean isValidContactNo = contactNo.matches(contactNoPattern);

//        txtEmployeeId.setStyle(txtEmployeeId.getStyle() + " -fx-background-color: #7367F0;");
//        txtNme.setStyle(txtNme.getStyle() + " -fx-background-color: #7367F0");
//        txtRole.setStyle(txtRole.getStyle() + " -fx-background-color: #7367F0");
//        txtPassword.setStyle(txtPassword.getStyle() + " -fx-background-color: #7367F0");
//        txtContactNo.setStyle(txtContactNo.getStyle() + " -fx-background-color: #7367F0");


        if (!isValidContactNo){
            txtContactNo.setStyle(txtContactNo.getStyle() + "; -fx-border-color: red;");
        }

        if ( isValidContactNo ){
            UserDto userDto = new UserDto(UserID, EmployeeID, name, role, password, contactNo);

            boolean isUpdate = userBO.updateUser(userDto);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "User updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update user...!").show();
            }
        }
    }


    @FXML
    void onClickTable(MouseEvent event) {
        UserTM selectedItem = tblUser.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblUserId.setText(selectedItem.getUserID());
            txtEmployeeId.setText(selectedItem.getEmployeeID());
            txtNme.setText(selectedItem.getName());
            txtRole.setText(selectedItem.getRole());
            txtPassword.setText(selectedItem.getPassword());
            txtContactNo.setText(selectedItem.getContactNo());

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

}

