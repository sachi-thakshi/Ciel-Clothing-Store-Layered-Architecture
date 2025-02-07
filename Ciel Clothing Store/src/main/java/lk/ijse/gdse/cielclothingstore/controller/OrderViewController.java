package lk.ijse.gdse.cielclothingstore.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse.cielclothingstore.bo.BOFactory;
import lk.ijse.gdse.cielclothingstore.bo.custom.CustomerBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.OrderBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.ProductBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.OrderBOImpl;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.ProductBOImpl;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dto.OrderDetailsDto;
import lk.ijse.gdse.cielclothingstore.dto.OrderDto;
import lk.ijse.gdse.cielclothingstore.dto.ProductDto;
import lk.ijse.gdse.cielclothingstore.view.tm.CartTM;
import lk.ijse.gdse.cielclothingstore.service.OrderDataSingleton;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderViewController implements Initializable {

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbProductId;

    @FXML
    private TableColumn<CartTM, String> colProductId;

    @FXML
    private TableColumn<CartTM, String> colName;

    @FXML
    private TableColumn<CartTM, Integer> colQuantity;

    @FXML
    private TableColumn<CartTM, Double> colPrice;

    @FXML
    private TableColumn<CartTM, Double> colTotal;

    @FXML
    private TableColumn<CartTM, Button> colAction;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label orderDate;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblProductName;

    @FXML
    private Label lblProductQty;

    @FXML
    private Label lblProductPrice;

    @FXML
    private TextField txtAddToCartQty;

    @FXML
    private Label lblFullAmount;

    @FXML
    private AnchorPane mainAnchorPane;

//    private final OrderModel orderModel = new OrderModel();
//    private final CustomerModel customerModel = new CustomerModel();
//    private final ProductModel productModel = new ProductModel();
    private OrderDataSingleton orderDataSingleton = OrderDataSingleton.getInstance();  // Singleton for order data

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    private final OrderBO orderBO = new OrderBOImpl();
    private final CustomerBO customerBO = new CustomerBOImpl();
    private final ProductBO productBO = new ProductBOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load initial data!").show();
            e.printStackTrace();
        }
    }

    private void setCellValues() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeButton"));
        tblCart.setItems(cartTMS);
    }

    private void refreshPage() throws SQLException {
        lblOrderId.setText(orderBO.getNextOrderId());
        orderDate.setText(LocalDate.now().toString());

        loadCustomerIds();
        loadProductIds();


        cmbCustomerId.getSelectionModel().clearSelection();
        cmbProductId.getSelectionModel().clearSelection();
        lblProductName.setText("");
        lblProductQty.setText("");
        lblProductPrice.setText("");
        lblCustomerName.setText("");
        txtAddToCartQty.clear();

        cartTMS.clear();
        tblCart.refresh();

        lblFullAmount.setText("Total Amount: Rs. 0.00");  // Initialize total amount label
    }

    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerBO.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList(customerIds);
        cmbCustomerId.setItems(observableList);
    }


    private void loadProductIds() throws SQLException {
        ArrayList<String> productIds = productBO.getAllProductIds();
        ObservableList<String> observableList = FXCollections.observableArrayList(productIds);
        cmbProductId.setItems(observableList);
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String selectedCustomerId = cmbCustomerId.getValue();
        if (selectedCustomerId != null) {
            try {
                CustomerDto customerDto = customerBO.findById(selectedCustomerId);
                if (customerDto != null) {
                    lblCustomerName.setText(customerDto.getName());
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to load customer details!").show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void cmbProductOnAction(ActionEvent event) {
        String selectedProductId = cmbProductId.getValue();
        if (selectedProductId != null) {
            try {
                ProductDto productDto = productBO.findById(selectedProductId);
                if (productDto != null) {
                    lblProductName.setText(productDto.getName());
                    lblProductQty.setText(String.valueOf(productDto.getQuantity()));
                    lblProductPrice.setText(String.valueOf(productDto.getPrice()));
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to load product details!").show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedProductId = cmbProductId.getValue();

        if (selectedProductId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a product!").show();
            return;
        }

        if (!txtAddToCartQty.getText().matches("^[0-9]+$")) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid quantity!").show();
            return;
        }

        int cartQuantity = Integer.parseInt(txtAddToCartQty.getText());
        int qtyOnHand = Integer.parseInt(lblProductQty.getText());

        if (cartQuantity > qtyOnHand) {
            new Alert(Alert.AlertType.ERROR, "Insufficient stock!").show();
            return;
        }

        double price = Double.parseDouble(lblProductPrice.getText());
        double total = cartQuantity * price;

        // Check if the product is already in the cart
        for (CartTM cartTM : cartTMS) {
            if (cartTM.getProductID().equals(selectedProductId)) {
                int newQuantity = cartTM.getCartQuantity() + cartQuantity;
                cartTM.setCartQuantity(newQuantity);
                cartTM.setTotal(newQuantity * price);
                tblCart.refresh();
                updateTotalAmount(); // Update the full amount
                return;
            }
        }

        // If it's a new product, add it to the cart
        Button removeButton = new Button("Remove");
        CartTM newCartItem = new CartTM(selectedProductId, lblProductName.getText(), cartQuantity, price, total, removeButton);

        removeButton.setOnAction(e -> {
            cartTMS.remove(newCartItem);
            tblCart.refresh();
            updateTotalAmount(); // Update the full amount after removal
        });

        cartTMS.add(newCartItem);
        txtAddToCartQty.clear();

        // Update the total amount after adding new item
        updateTotalAmount();
    }

    private double calculateTotalAmount() {
        double totalAmount = 0;
        for (CartTM cartTM : cartTMS) {
            totalAmount += cartTM.getCartQuantity() * cartTM.getPrice();
        }
        return totalAmount;
    }

    private void updateTotalAmount() {
        double totalAmount = calculateTotalAmount(); // Calculate the total
        lblFullAmount.setText(" Rs." + totalAmount); // Set the total amount to the label
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        if (cartTMS.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "The cart is empty. Please add items before placing the order.").show();
            return;
        }

        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        String customerId = cmbCustomerId.getValue();  // Get selected customer ID
        Date dateOfOrder = Date.valueOf(orderDate.getText());

        // Fetch the customer data using the selected customer ID
        CustomerDto customerDto = customerBO.findById(customerId);

        if (customerDto == null) {
            new Alert(Alert.AlertType.ERROR, "Customer not found!").show();
            return;
        }

        ArrayList<OrderDetailsDto> orderDetailsList = new ArrayList<>();
        for (CartTM cartTM : cartTMS) {
            orderDetailsList.add(new OrderDetailsDto(orderId, cartTM.getProductID(), cartTM.getCartQuantity(), cartTM.getPrice()));
        }

        OrderDto orderDto = new OrderDto(orderId, customerId, dateOfOrder, orderDetailsList);

        // Update Singleton with order data
        OrderDataSingleton orderData = OrderDataSingleton.getInstance();
        orderData.setOrderId(orderId);
        orderData.setTotalAmount(calculateTotalAmount()); // Calculate the total amount

        // Print or debug log
        System.out.println("Placing order with Order ID: " + orderId + " and Total Amount: Rs. " + orderData.getTotalAmount());

        try {
            if (orderBO.saveOrder(orderDto)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!");
                alert.setOnHidden(e -> openPaymentPopup(customerDto)); // Pass customer details to payment popup
                alert.show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to place the order!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while placing the order!").show();
            e.printStackTrace();
        }
    }


    private void openPaymentPopup(CustomerDto customerDto) {
        try {
            // Load the Payment FXML page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PaymentView.fxml"));
            AnchorPane root = loader.load();

            // Get the controller of the PaymentView
            PaymentViewController paymentViewController = loader.getController();

            // Retrieve order data from the Singleton
            OrderDataSingleton orderData = OrderDataSingleton.getInstance();
            String orderId = orderData.getOrderId();
            double totalAmount = orderData.getTotalAmount();

            // Pass data to PaymentViewController
            paymentViewController.setPaymentDetails(orderId, totalAmount, customerDto);  // Pass customer details here

            // Set up the Stage (popup window) for the Payment
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Ensures the payment window is modal (blocks interaction with the parent)
            stage.setScene(new Scene(root));
            stage.setTitle("Payment");
            stage.showAndWait();  // Show the window and wait for the user to close it

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }
}

