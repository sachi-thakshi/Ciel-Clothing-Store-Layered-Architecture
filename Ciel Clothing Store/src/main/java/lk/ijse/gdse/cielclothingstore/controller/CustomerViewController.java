package lk.ijse.gdse.cielclothingstore.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse.cielclothingstore.bo.BOFactory;
import lk.ijse.gdse.cielclothingstore.bo.custom.CustomerBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.cielclothingstore.db.DBConnection;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.view.tm.CustomerTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

public class CustomerViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnGenerateReport;

    @FXML
    private Button btnOpenMailSendModel;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CustomerTM, String> colContactNo;

    @FXML
    private TableColumn<CustomerTM, String> colCustomerID;

    @FXML
    private TableColumn<CustomerTM, String> colEmail;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colPriority;

    @FXML
    private TableColumn<CustomerTM, String> colUserID;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNme;

    @FXML
    private TextField txtPriority;

    @FXML
    private TextField txtUserID;

//    CustomerModel customerModel = new CustomerModel();
    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));

        try {
            refreshPage();
            String nextCustomerID = customerBO.getNextCustomerId();
            System.out.println(nextCustomerID);
            lblCustomerId.setText(nextCustomerID);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {

        refreshTable();

        String nextCustomerID = customerBO.getNextCustomerId();
        lblCustomerId.setText(nextCustomerID);

        txtUserID.setText("");
        txtNme.setText("");
        txtPriority.setText("");
        txtContactNo.setText("");
        txtEmail.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnGenerateReport.setDisable(true);
    }

    private void refreshTable() throws SQLException{
        ArrayList<CustomerDto> customerDtos = customerBO.getAllCustomers();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDto customerDto : customerDtos) {
            CustomerTM customerTM = new CustomerTM(
                    customerDto.getCustomerID(),
                    customerDto.getUserID(),
                    customerDto.getName(),
                    customerDto.getPriority(),
                    customerDto.getContactNo(),
                    customerDto.getEmail()
            );
            customerTMS.add(customerTM);
        }

        tblCustomer.setItems(customerTMS);
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String customerID = lblCustomerId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = customerBO.deleteCustomer(customerID);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer...!").show();
            }
        }
    }

    @FXML
    void btnGenerateReportOnAction(ActionEvent event) {
        CustomerTM customerTM = tblCustomer.getSelectionModel().getSelectedItem();

        if (customerTM == null) {
            return;
        }
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/report/Customer Order Details Report .jrxml")
            );

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("P_Date",LocalDate.now().toString());
            parameters.put("P_Customer_ID",customerTM.getCustomerID());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException{
        refreshPage();
    }

    @FXML
    void btnSaveCustomerOnAction(ActionEvent event) throws ParseException, SQLException, ClassNotFoundException {
        String customerID = lblCustomerId.getText();
        String userID = txtUserID.getText();
        String name = txtNme.getText();
        String priority = txtPriority.getText();
        String contactNo = txtContactNo.getText();
        String email = txtEmail.getText();

        String priorityPattern = "^(loyalty|non loyalty)$";
        String contactNoPattern = "^\\d{3}-\\d{7}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidPriority = priority.matches(priorityPattern);
        boolean isValidContactNo = contactNo.matches(contactNoPattern);
        boolean isValidEmail = email.matches(emailPattern);

//        txtUserID.setStyle(txtUserID.getStyle() + " -fx-background-color: #7367F0;");
//        txtNme.setStyle(txtNme.getStyle() + " -fx-background-color: #7367F0");
//        txtPriority.setStyle(txtPriority.getStyle() + " -fx-background-color: #7367F0");
//        txtContactNo.setStyle(txtContactNo.getStyle() + " -fx-background-color: #7367F0");
//        txtDob.setStyle(txtDob.getStyle() + " -fx-background-color: #7367F0");

        if (!isValidPriority){
            txtPriority.setStyle(txtPriority.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidContactNo){
            txtContactNo.setStyle(txtContactNo.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle() + "; -fx-border-color: red;");
        }

        if ( isValidPriority && isValidContactNo && isValidEmail){
            CustomerDto customerDto = new CustomerDto(customerID, userID, name, priority, contactNo, email);

            boolean isSaved = customerBO.saveCustomer(customerDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save customer...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String customerID = lblCustomerId.getText();
        String userID = txtUserID.getText();
        String name = txtNme.getText();
        String priority = txtPriority.getText();
        String contactNo = txtContactNo.getText();
        String email = txtEmail.getText();

        String priorityPattern = "^(loyalty|non loyalty)$";
        String contactNoPattern = "^\\d{3}-\\d{7}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidPriority = priority.matches(priorityPattern);
        boolean isValidContactNo = contactNo.matches(contactNoPattern);
        boolean isValidEmail = email.matches(emailPattern);

//        txtUserID.setStyle(txtUserID.getStyle() + " -fx-background-color: #7367F0;");
//        txtNme.setStyle(txtNme.getStyle() + " -fx-background-color: #7367F0");
//        txtPriority.setStyle(txtPriority.getStyle() + " -fx-background-color: #7367F0");
//        txtContactNo.setStyle(txtContactNo.getStyle() + " -fx-background-color: #7367F0");
//        txtDob.setStyle(txtDob.getStyle() + " -fx-background-color: #7367F0");

        if (!isValidPriority){
            txtPriority.setStyle(txtPriority.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidContactNo){
            txtContactNo.setStyle(txtContactNo.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle() + "; -fx-border-color: red;");
        }

        if ( isValidPriority && isValidContactNo && isValidEmail){
            CustomerDto customerDto = new CustomerDto(customerID, userID, name, priority, contactNo, email);

            boolean isUpdate = customerBO.updateCustomer(customerDto);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Customer updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
            }
        }
    }

    @FXML
    void generateAllCustomerReportOnAction(ActionEvent event) {

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/report/Customer Report.jrxml")
            );

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("P_Date",LocalDate.now());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void onClickTable(MouseEvent event) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblCustomerId.setText(selectedItem.getCustomerID());
            txtUserID.setText(selectedItem.getUserID());
            txtNme.setText(selectedItem.getName());
            txtPriority.setText(selectedItem.getPriority());
            txtContactNo.setText(selectedItem.getContactNo());
            txtEmail.setText(selectedItem.getEmail());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnGenerateReport.setDisable(false);
        }
    }

    @FXML
    void btnOpenMailSendModelOnAction(ActionEvent event) {
        // Get the selected customer from the customer table
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            // Show warning if no customer is selected
            new Alert(Alert.AlertType.WARNING, "Please select a customer..!").show();
            return;
        }

        try {
            // Load the mail dialog from FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SendMailView.fxml"));
            Parent load = loader.load();

            // Get the controller for the FXML
            SendMailController sendMailController = loader.getController();

            // Get the selected customer's email
            String email = selectedItem.getEmail();

            // Set the customer's email in the SendMailController (pre-fills the recipient email)
            sendMailController.setCustomerEmail(email);

            // Create a new stage (window) for the popup
            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send Email");

            // Make the stage modal (blocks interaction with the parent window)
            stage.initModality(Modality.APPLICATION_MODAL);

            // Set the current window as the owner of the modal window
            Window underWindow = tblCustomer.getScene().getWindow();
            stage.initOwner(underWindow);

            // Show the modal window
            stage.showAndWait();
        } catch (IOException e) {
            // Handle the error if the FXML fails to load
            new Alert(Alert.AlertType.ERROR, "Failed to load UI..!").show();
            e.printStackTrace();
        }
    }

}
