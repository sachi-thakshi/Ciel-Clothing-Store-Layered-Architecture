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
import lk.ijse.gdse.cielclothingstore.bo.custom.EmployeeBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.EmployeeBOImpl;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dto.EmployeeDto;
import lk.ijse.gdse.cielclothingstore.view.tm.EmployeeTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<EmployeeTM, String> colContactNo;

    @FXML
    private TableColumn<EmployeeTM, String> colEmployeeId;

    @FXML
    private TableColumn<EmployeeTM, String> colName;

    @FXML
    private TableColumn<EmployeeTM, String> colRole;

    @FXML
    private TableColumn<EmployeeTM, Double> colSalary;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtSalary;

//    EmployeeModel employeeModel = new EmployeeModel();
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));

        try {
            refreshPage();
            String nextEmployeeID = employeeBO.getNextEmployeeId();
            System.out.println(nextEmployeeID);
            lblEmployeeId.setText(nextEmployeeID);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTableData() throws SQLException {
        ArrayList<EmployeeDto> employeeDtos = employeeBO.getAllEmployees();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDto employeeDto : employeeDtos) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDto.getEmployeeID(),
                    employeeDto.getName(),
                    employeeDto.getSalary(),
                    employeeDto.getRole(),
                    employeeDto.getContactNo()
            );

            employeeTMS.add(employeeTM);
        }

        tblEmployee.setItems(employeeTMS);
    }

    private void loadNextEmployeeId() throws SQLException {
        String nextEmployeeID = employeeBO.getNextEmployeeId();
        lblEmployeeId.setText(nextEmployeeID);
    }
    private void refreshPage() throws SQLException {
        loadNextEmployeeId();
        loadTableData();
        resetStyles();

        txtName.setText("");
        txtSalary.setText("");
        txtRole.setText("");
        txtContactNo.setText("");
        tblEmployee.getSelectionModel().clearSelection();

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void resetStyles() {
        txtName.setStyle(txtName.getStyle() + "; -fx-border-color: #7367F0;");
        txtSalary.setStyle(txtSalary.getStyle() + "; -fx-border-color: #7367F0;");
        txtRole.setStyle(txtRole.getStyle() + "; -fx-border-color: #7367F0;");
        txtContactNo.setStyle(txtContactNo.getStyle() + "; -fx-border-color: #7367F0;");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String EmployeeID = lblEmployeeId.getText();

        if (EmployeeID != null) {
            // Confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Employee?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    // Call the deleteEmployee method from the EmployeeModel
                    boolean isDeleted = employeeBO.deleteEmployee(EmployeeID);

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully!").show();
                        refreshPage(); // Refresh table and fields
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete employee!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "An error occurred while deleting the employee: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No employee selected to delete!").show();
        }
    }


    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String employeeId = lblEmployeeId.getText();
        String name = txtName.getText();
        String salaryString = txtSalary.getText();
        String role = txtRole.getText();
        String contactNo = txtContactNo.getText();

        // Define regex patterns for validation
        String contactNoPattern = "^\\d{3}-\\d{7}$";

        // Validate fields
        boolean isValidName = name != null && !name.trim().isEmpty();
        boolean isValidContactNo = contactNo.matches(contactNoPattern);
        boolean isValidSalary = false;
        double salary = 0.0;

        try {
            salary = Double.parseDouble(salaryString);
            isValidSalary = salary > 0;
        } catch (NumberFormatException e) {
            isValidSalary = false;
        }

        resetStyles();

        // Highlight invalid fields
        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidContactNo) {
            txtContactNo.setStyle(txtContactNo.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidSalary) {
            txtSalary.setStyle(txtSalary.getStyle() + ";-fx-border-color: red;");
        }

        // Save employee if all fields are valid
        if (isValidName && isValidContactNo && isValidSalary) {
            resetStyles();

            EmployeeDto employeeDto = new EmployeeDto(employeeId, name, salary, role, contactNo);
            boolean isSaved = false;
            try {
                isSaved = employeeBO.saveEmployee(employeeDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee saved successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save employee!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String employeeId = lblEmployeeId.getText();
        String name = txtName.getText();
        String salaryString = txtSalary.getText();
        String role = txtRole.getText();
        String contactNo = txtContactNo.getText();

        // Define regex patterns for validation
        String contactNoPattern = "^\\d{3}-\\d{7}$";

        // Validate fields
        boolean isValidName = name != null && !name.trim().isEmpty();
        boolean isValidContactNo = contactNo.matches(contactNoPattern);
        boolean isValidSalary = false;
        double salary = 0.0;

        try {
            salary = Double.parseDouble(salaryString);
            isValidSalary = salary > 0;
        } catch (NumberFormatException e) {
            isValidSalary = false;
        }

        resetStyles();

        // Highlight invalid fields
        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidContactNo) {
            txtContactNo.setStyle(txtContactNo.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidSalary) {
            txtSalary.setStyle(txtSalary.getStyle() + ";-fx-border-color: red;");
        }

        // Update employee if all fields are valid
        if (isValidName && isValidContactNo && isValidSalary) {
            resetStyles();

            EmployeeDto employeeDto = new EmployeeDto(employeeId, name, salary, role, contactNo);

            boolean isUpdated = false;
            try {
                isUpdated = employeeBO.updateEmployee(employeeDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update employee!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblEmployeeId.setText(selectedItem.getEmployeeID());
            txtName.setText(selectedItem.getName());
            txtSalary.setText(String.valueOf(selectedItem.getSalary()));
            txtRole.setText(selectedItem.getRole());
            txtContactNo.setText(selectedItem.getContactNo());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

}

