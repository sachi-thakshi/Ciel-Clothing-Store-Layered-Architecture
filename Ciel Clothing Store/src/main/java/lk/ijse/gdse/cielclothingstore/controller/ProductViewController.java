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
import lk.ijse.gdse.cielclothingstore.bo.custom.ProductBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.ProductBOImpl;
import lk.ijse.gdse.cielclothingstore.dto.ProductDto;
import lk.ijse.gdse.cielclothingstore.view.tm.ProductTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductViewController  implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<ProductTM, String> colDesc;

    @FXML
    private TableColumn<ProductTM, String> colName;

    @FXML
    private TableColumn<ProductTM, Double> colPrice;

    @FXML
    private TableColumn<ProductTM, String> colProductId;

    @FXML
    private TableColumn<ProductTM, Integer> colQty;

    @FXML
    private TableColumn<ProductTM, String> colSuppierID;

    @FXML
    private Label lblProductId;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TableView<ProductTM> tblProduct;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtSupplierId;

//    ProductModel productModel = new ProductModel();
    ProductBO productBO = new ProductBOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colProductId.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colSuppierID.setCellValueFactory(new PropertyValueFactory<>("SupplierID"));

        try {
            refreshPage();
            String nextProductID = productBO.getNextProductId();
            System.out.println(nextProductID);
            lblProductId.setText(nextProductID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTableData() throws SQLException {
        ArrayList<ProductDto> productDtos = productBO.getAllProducts();
        ObservableList<ProductTM> productTMS = FXCollections.observableArrayList();

        for (ProductDto productDto : productDtos) {
            ProductTM productTM = new ProductTM(
                    productDto.getProductID(),
                    productDto.getName(),
                    productDto.getPrice(),
                    productDto.getDescription(),
                    productDto.getQuantity(),
                    productDto.getSupplierID()
            );
            productTMS.add(productTM);
        }
        tblProduct.setItems(productTMS);
    }

    private void loadNextProductId() throws SQLException {
        String nextProductId = productBO.getNextProductId();
        lblProductId.setText(nextProductId);
    }

    private void refreshPage() throws SQLException {
        loadTableData();
        loadNextProductId();
        resetStyles();

        txtName.setText("");
        txtPrice.setText("");
        txtDesc.setText("");
        txtQuantity.setText("");
        txtSupplierId.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void resetStyles() {
        txtName.setStyle(txtName.getStyle() + "; -fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + "; -fx-border-color: #7367F0;");
        txtDesc.setStyle(txtDesc.getStyle() + "; -fx-border-color: #7367F0");
        txtQuantity.setStyle(txtQuantity.getStyle() + "; -fx-border-color: #7367F0;");
        txtSupplierId.setStyle(txtSupplierId.getStyle() + "; -fx-border-color: #7367F0;");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String productId = lblProductId.getText();

        if (productId != null) {
            // Confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    boolean isDeleted = productBO.deleteProduct(productId);

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Product deleted successfully!").show();
                        refreshPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete product!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "An error occurred while deleting the product:" + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No product selected to delete!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        // Retrieve the input values from the form fields
        String productId = lblProductId.getText();
        String name = txtName.getText();
        String priceString = txtPrice.getText();
        String description = txtDesc.getText();
        String quantityString = txtQuantity.getText();
        String supplierId = txtSupplierId.getText();

        // Define the regex patterns for validation
        String namePattern = "^[A-Za-z0-9%.,'\"()\\-\\s]+$";
        String pricePattern = "^(\\d+(\\.\\d{1,2})?)$";
        String descriptionPattern = "^[A-Za-z0-9%.,'\"()\\-\\s]+$";
        String quantityPattern = "^[0-9]+$";

        // Validate each input field using the defined regex patterns
        boolean isValidName = name.matches(namePattern);
        boolean isValidPrice = priceString.matches(pricePattern);
        boolean isValidDescription = description.matches(descriptionPattern);
        boolean isValidQuantity = quantityString.matches(quantityPattern);

        // Reset previous error styles
        resetStyles();

        // Apply red border style if the validation fails for any field
        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + "; -fx-border-color: red;");
        }
        if (!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + "; -fx-border-color: red;");
        }
        if (!isValidDescription) {
            txtDesc.setStyle(txtDesc.getStyle() + "; -fx-border-color: red;");
        }
        if (!isValidQuantity) {
            txtQuantity.setStyle(txtQuantity.getStyle() + "; -fx-border-color: red;");
        }

        // If all fields are valid, proceed to save the product
        if ( isValidName && isValidPrice && isValidDescription && isValidQuantity) {
            resetStyles();

            // Parse the quantity and price fields into appropriate data types
            int quantity = Integer.parseInt(quantityString);
            double price = Double.parseDouble(priceString);

            // Create a new ProductDto object with the valid data
            ProductDto productDto = new ProductDto(productId, name, price, description, quantity, supplierId);

            try {
                // Call saveProduct method in ProductModel to save the product
                boolean isSaved = productBO.saveProduct(productDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Product saved successfully!").show();
                    refreshPage(); // Refresh the page to reflect the changes
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save product!").show();
                }
            } catch (SQLException e) {
                // Handle any SQL exceptions that occur during the save operation
                new Alert(Alert.AlertType.ERROR, "Error occurred while saving the product: " + e.getMessage()).show();
            }
        } else {
            // Show a general error message if any of the fields are invalid
            new Alert(Alert.AlertType.WARNING, "Please fill all fields correctly.").show();
        }
    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        // Retrieve the input values from the form fields
        String productId = lblProductId.getText();
        String name = txtName.getText();
        String priceString = txtPrice.getText();
        String description = txtDesc.getText();
        String quantityString = txtQuantity.getText();
        String supplierId = txtSupplierId.getText();

        // Define the regex patterns for validation
        String namePattern = "^[A-Za-z0-9%.,'\"()\\-\\s]+$";
        String pricePattern = "^(\\d+(\\.\\d{1,2})?)$";
        String descriptionPattern = "^[A-Za-z0-9%.,'\"()\\-\\s]+$";
        String quantityPattern = "^[0-9]+$";

        // Validate each input field using the defined regex patterns
        boolean isValidName = name.matches(namePattern);
        boolean isValidPrice = priceString.matches(pricePattern);
        boolean isValidDescription = description.matches(descriptionPattern);
        boolean isValidQuantity = quantityString.matches(quantityPattern);

        // Reset previous error styles
        resetStyles();

        // Apply red border style if the validation fails for any field
        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + "; -fx-border-color: red;");
        }
        if (!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + "; -fx-border-color: red;");
        }
        if (!isValidDescription) {
            txtDesc.setStyle(txtDesc.getStyle() + "; -fx-border-color: red;");
        }
        if (!isValidQuantity) {
            txtQuantity.setStyle(txtQuantity.getStyle() + "; -fx-border-color: red;");
        }

        // If all fields are valid, proceed to save the product
        if ( isValidName && isValidPrice && isValidDescription && isValidQuantity) {
            resetStyles();

            // Parse the quantity and price fields into appropriate data types
            int quantity = Integer.parseInt(quantityString);
            double price = Double.parseDouble(priceString);

            // Create a new ProductDto object with the valid data
            ProductDto productDto = new ProductDto(productId, name, price, description, quantity, supplierId);

            try {
                // Call saveProduct method in ProductModel to save the product
                boolean isUpdated = productBO.updateProduct(productDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Product updated successfully!").show();
                    refreshPage(); // Refresh the page to reflect the changes
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update product!").show();
                }
            } catch (SQLException e) {
                // Handle any SQL exceptions that occur during the save operation
                new Alert(Alert.AlertType.ERROR, "Error occurred while updating the product: " + e.getMessage()).show();
            }
        } else {
            // Show a general error message if any of the fields are invalid
            new Alert(Alert.AlertType.WARNING, "Please fill all fields correctly.").show();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        ProductTM productTM = tblProduct.getSelectionModel().getSelectedItem();
        if (productTM != null) {
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);

            lblProductId.setText(productTM.getProductID());
            txtName.setText(productTM.getName());
            txtPrice.setText(String.valueOf(productTM.getPrice()));
            txtDesc.setText(productTM.getDescription());
            txtQuantity.setText(String.valueOf(productTM.getQuantity()));
            txtSupplierId.setText(productTM.getSupplierID());
        }
    }

}



