package lk.ijse.gdse.cielclothingstore.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.cielclothingstore.bo.BOFactory;
import lk.ijse.gdse.cielclothingstore.bo.custom.SupplierBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.SuppilerBOImpl;
import lk.ijse.gdse.cielclothingstore.dto.SuppliersDto;
import lk.ijse.gdse.cielclothingstore.view.tm.SupplierTM;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SupplierTM, String> colAddress;

    @FXML
    private TableColumn<SupplierTM, String> colContactNo;

    @FXML
    private TableColumn<SupplierTM, String> colName;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierId;

    @FXML
    private Label lblSupplierId;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtName;

//    SupplierModel supplierModel = new SupplierModel();
    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));

        try {
            refreshPage();
            String nextSupplierID = supplierBO.getNextSupplierId();
            System.out.println(nextSupplierID);
            lblSupplierId.setText(nextSupplierID);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {

        refreshTable();

        String nextSupplierID = supplierBO.getNextSupplierId();
        lblSupplierId.setText(nextSupplierID);

        txtName.setText("");
        txtContactNo.setText("");
        txtAddress.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<SuppliersDto> suppliersDtos = supplierBO.getAllSuppliers();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SuppliersDto suppliersDto : suppliersDtos) {
            SupplierTM supplierTM = new SupplierTM(
                    suppliersDto.getSupplierID(),
                    suppliersDto.getName(),
                    suppliersDto.getContactNo(),
                    suppliersDto.getAddress()
            );
            supplierTMS.add(supplierTM);
        }

        tblSupplier.setItems(supplierTMS);
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String supplierID = lblSupplierId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this supplier?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = supplierBO.deleteSupplier(supplierID);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete supplier...!").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String supplierID = lblSupplierId.getText();
        String name = txtName.getText();
        String contactNo = txtContactNo.getText();
        String address = txtAddress.getText();
        ;

        String contactNoPattern = "^\\d{3}-\\d{7}$";

        boolean isValidContactNo = contactNo.matches(contactNoPattern);

//        txtName.setStyle(txtName.getStyle() + " -fx-background-color: #7367F0;");
//        txtContactNo.setStyle(txtContactNo.getStyle() + " -fx-background-color: #7367F0");
//        txtAddress.setStyle(txtAddress.getStyle() + " -fx-background-color: #7367F0");

        if (!isValidContactNo){
            txtContactNo.setStyle(txtContactNo.getStyle() + "; -fx-border-color: red;");
        }


        if ( isValidContactNo ){
            SuppliersDto suppliersDto = new SuppliersDto(supplierID, name, contactNo, address);

            boolean isSaved = supplierBO.saveSupplier(suppliersDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save supplier...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String supplierID = lblSupplierId.getText();
        String name = txtName.getText();
        String contactNo = txtContactNo.getText();
        String address = txtAddress.getText();
        ;

        String contactNoPattern = "^\\d{3}-\\d{7}$";

        boolean isValidContactNo = contactNo.matches(contactNoPattern);

//        txtName.setStyle(txtName.getStyle() + " -fx-background-color: #7367F0;");
//        txtContactNo.setStyle(txtContactNo.getStyle() + " -fx-background-color: #7367F0");
//        txtAddress.setStyle(txtAddress.getStyle() + " -fx-background-color: #7367F0");

        if (!isValidContactNo){
            txtContactNo.setStyle(txtContactNo.getStyle() + "; -fx-border-color: red;");
        }


        if ( isValidContactNo ){
            SuppliersDto suppliersDto = new SuppliersDto(supplierID, name, contactNo, address);

            boolean isUpdated = supplierBO.updateSupplier(suppliersDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update supplier...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        SupplierTM selectedItem = tblSupplier.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblSupplierId.setText(selectedItem.getSupplierID());
            txtName.setText(selectedItem.getName());
            txtContactNo.setText(selectedItem.getContactNo());
            txtAddress.setText(selectedItem.getAddress());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

}

