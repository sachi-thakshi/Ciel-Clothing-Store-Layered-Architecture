package lk.ijse.gdse.cielclothingstore.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.cielclothingstore.bo.BOFactory;
import lk.ijse.gdse.cielclothingstore.bo.custom.PaymentBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.UserBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.PaymentBOImpl;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.UserBOImpl;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dto.PaymentDto;
import lk.ijse.gdse.cielclothingstore.dto.UserDto;
import lk.ijse.gdse.cielclothingstore.view.tm.PaymentTM;
import lk.ijse.gdse.cielclothingstore.service.OrderDataSingleton;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class PaymentViewController implements Initializable {

    @FXML
    private Label PaymentId;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnPayment;

    @FXML
    private ComboBox<String> cmbUserId;

    @FXML
    private TableColumn<PaymentTM, String> colMehtod;

    @FXML
    private TableColumn<PaymentTM, String> colAmount;

    @FXML
    private TableColumn<PaymentTM, String> colOrderId;

    @FXML
    private TableColumn<PaymentTM, Date> colPDate;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;


    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblNTotal;

    @FXML
    private Label lblOrderID;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblProductPrice;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lblpaymentDate;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TextField txtMethod;

    @FXML
    private TableView<PaymentTM> tblPayment;

//    private UserModel userModel = new UserModel();
//    private PaymentModel paymentModel = new PaymentModel();
    private OrderDataSingleton orderService = OrderDataSingleton.getInstance();

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USERACCOUNT);
    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    private final ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        tblPayment.setItems(orderService.getPayments());
        try {
            refreshPage();
            loadUserIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValues() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colPDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMehtod.setCellValueFactory(new PropertyValueFactory<>("method"));
    }

    private void refreshPage() throws SQLException {
        lblPaymentId.setText(paymentBO.getNextPaymentId());
        lblpaymentDate.setText(LocalDate.now().toString());

        loadUserIds();

        cmbUserId.getSelectionModel().clearSelection();
        lblUserName.setText("");
        lblProductPrice.setText("");
        lblDiscount.setText("");
        lblNTotal.setText("");
        lblOrderID.setText("");
        txtMethod.clear();
    }

    private void loadUserIds() throws SQLException {
        ArrayList<String> userIds = userBO.getAllUserIds();
        ObservableList<String> observableList = FXCollections.observableArrayList(userIds);
        cmbUserId.setItems(observableList);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        try {
            // Retrieve and validate payment details
            String paymentId = lblPaymentId.getText();
            String orderId = lblOrderID.getText();
            String customerId = cmbUserId.getValue();
            String customerName = lblUserName.getText();
            String total = lblNTotal.getText().replace("Rs. ", "").trim();

            if (paymentId.isEmpty() || orderId.isEmpty() || customerId == null || total.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please ensure all fields are filled before proceeding!").show();
                return;
            }

            // Prepare payment data
            double totalAmount = Double.parseDouble(total);
            String paymentDetails = (lblDiscount.getText().isEmpty()) ? "Non-Loyalty" : "Loyalty Customer";
            String paymentDateStr = lblpaymentDate.getText(); // Payment date as String
            java.sql.Date paymentDate = java.sql.Date.valueOf(paymentDateStr); // Convert to SQL Date
            java.sql.Time paymentTime = java.sql.Time.valueOf(java.time.LocalTime.now()); // Get current time

            // Save payment details in the database
            boolean isSaved = paymentBO.savePayment(new PaymentDto(
                    paymentId,
                    orderId,
                    totalAmount,
                    paymentDate,
                    paymentTime
            ));

            // Alert message based on save status
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Payment successful!").show();
                closeWindow(); // Optional: close the payment window
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to process the payment! Please try again.").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while processing the payment!").show();
            e.printStackTrace();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid total amount format!").show();
        }
    }

    // Utility method to close the payment window
    private void closeWindow() {
        Stage stage = (Stage) mainAnchorPane.getScene().getWindow();
        stage.close();
    }


    @FXML
    void cmbUserOnAction(ActionEvent event) throws SQLException {
        String selectedUserId = cmbUserId.getValue();
        if (selectedUserId != null) {

            UserDto userDto = userBO.findById(selectedUserId);
            if (userDto != null) {
                lblUserName.setText(userDto.getName());
            }
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        // Get the payment method entered in the text field
        String paymentMethod = txtMethod.getText().trim(); // Get the payment method, trim spaces

        // Validate the payment method (either "card" or "cash")
        if (!paymentMethod.matches("^(card|cash)$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid payment method! Please enter 'card' or 'cash'").show();
            return; // Exit the method if the payment method is invalid
        }

        // Get the Order ID and Total Amount (from labels set by setPaymentDetails)
        String orderId = lblOrderID.getText();
        String paymentId = lblPaymentId.getText();
        String amountText = lblNTotal.getText().replace(" Rs.", "").trim(); // Remove Rs. and trim spaces
        double amount;

        // Validate the amount field
        try {
            amount = Double.parseDouble(amountText); // Parse the amount
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid amount! Please enter a valid number.").show();
            return; // Exit the method if the amount is invalid
        }

        // Check if the row for the current order already exists
        boolean orderFound = false;
        for (PaymentTM paymentTM : paymentTMS) {
            if (paymentTM.getOrderID().equals(orderId)) {
                // If the order is found, update the row with the payment method and amount
                paymentTM.setMethod(paymentMethod);
                paymentTM.setAmount(amount);
                orderFound = true;
                break; // No need to continue searching once we've found the row
            }
        }

        // If the order wasn't found, create a new payment row and add it to the table
        if (!orderFound) {
            PaymentTM newPayment = new PaymentTM(paymentId, orderId, new Date(), amount, paymentMethod);
            paymentTMS.add(newPayment);
        }

        // Refresh the table to show the updated row or the new row
        tblPayment.refresh();  // Refresh the table to reflect the updated data

    }

    // This method will set the payment details, including applying the discount and updating the total
    public void setPaymentDetails(String orderId, double totalAmount, CustomerDto customerDto) {
        // Set Order ID and total amount before discount
        lblOrderID.setText(orderId);
        lblProductPrice.setText(" Rs. " + totalAmount); // Display total amount in Rs.

        // Check if the customer has a "loyalty" status and apply the discount
        if ("loyalty".equalsIgnoreCase(customerDto.getPriority())) {
            applyDiscount(totalAmount);
        } else {
            // If not a loyalty customer, no discount
            lblDiscount.setText("0.00");
            lblNTotal.setText(lblProductPrice.getText()); // Just show the original total
            addPaymentToTable(orderId, totalAmount); // Add the full amount to the table
        }
    }

    // This method applies a 5% discount for loyalty customers
    private void applyDiscount(double totalAmount) {
        // Calculate the 5% discount for loyalty customers
        double discount = totalAmount * 0.05;
        double discountedTotal = totalAmount - discount;

        // Update the labels to show the discount and new total
        lblDiscount.setText(String.valueOf(discount));
        lblNTotal.setText(String.valueOf(discountedTotal));

        // Add the discounted total to the table
        addPaymentToTable(lblOrderID.getText(), discountedTotal);
    }

    // Method to add a new payment record to the table
    private void addPaymentToTable(String orderId, double amount) {
        // Get a new PaymentTM object with the current order ID and amount
        String paymentId = lblPaymentId.getText();  // Assuming you have a payment ID set
        String method = txtMethod.getText();  // Assuming you have a method (e.g., card or cash)
        Date paymentDate = new Date();  // Current date for payment

        // Create a new PaymentTM object and add it to the observable list
        PaymentTM paymentTM = new PaymentTM(paymentId, orderId, paymentDate, amount, method);
        paymentTMS.add(paymentTM);  // This will update the table automatically

        // Refresh the table to reflect the new entry
        tblPayment.setItems(paymentTMS);  // Make sure the table is showing the updated data
        tblPayment.refresh();
    }

}

