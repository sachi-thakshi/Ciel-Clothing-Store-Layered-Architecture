package lk.ijse.gdse.cielclothingstore.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.cielclothingstore.bo.BOFactory;
import lk.ijse.gdse.cielclothingstore.bo.custom.ProductDetailsBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.ProductDetailsBOImpl;
import lk.ijse.gdse.cielclothingstore.dto.ProductDetailsDto;
import lk.ijse.gdse.cielclothingstore.view.tm.ProductDetailsTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductDetailsViewController implements Initializable {

    @FXML
    private TableColumn<ProductDetailsTM, String> colProductId;

    @FXML
    private TableColumn<ProductDetailsTM, String> colSupplierId;

    @FXML
    private TableView<ProductDetailsTM> tblProductDetails;

//    private final ProductDetailsModel productDetailsModel = new ProductDetailsModel();
    private final ProductDetailsBO productDetailsBO = new ProductDetailsBOImpl();

    private final ObservableList<ProductDetailsTM> productDetailsList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up columns with the respective properties
        colProductId.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierID"));

        // Load data into the TableView
        loadProductDetails();
    }

    private void loadProductDetails() {
        try {
            ArrayList<ProductDetailsDto> productDetailsDtos = productDetailsBO.getAllProductDetails();
            for (ProductDetailsDto dto : productDetailsDtos) {
                productDetailsList.add(new ProductDetailsTM(
                        dto.getProductID(),
                        dto.getSupplierID()
                ));
            }
            tblProductDetails.setItems(productDetailsList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL errors gracefully, e.g., show an alert
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        // Handle row selection
        ProductDetailsTM selectedItem = tblProductDetails.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            System.out.println("Selected Product ID: " + selectedItem.getProductID());
            System.out.println("Selected Supplier ID: " + selectedItem.getSupplierID());
        }
    }

}

