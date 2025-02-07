package lk.ijse.gdse.cielclothingstore.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse.cielclothingstore.view.tm.PaymentTM;

public class OrderDataSingleton {

    // Step 1: Private static instance for Singleton pattern
    private static OrderDataSingleton instance;

    // Step 2: Variables to store order data
    private String orderId;
    private double totalAmount;

    // Observable list to store payments made for this order
    private ObservableList<PaymentTM> payments;

    // Step 3: Private constructor to prevent direct instantiation
    private OrderDataSingleton() {
        // Initialize the payments list
        payments = FXCollections.observableArrayList();
    }

    // Step 4: Public method to get the Singleton instance
    public static OrderDataSingleton getInstance() {
        if (instance == null) {
            instance = new OrderDataSingleton();
        }
        return instance;
    }

    // Step 5: Getter and Setter for orderId
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    // Step 6: Getter and Setter for totalAmount
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Step 7: Method to add a payment to the payments list
    public void addPayment(PaymentTM paymentTM) {
        payments.add(paymentTM);  // Add the PaymentTM object to the list
    }

    // Step 8: Getter for the payments list
    public ObservableList<PaymentTM> getPayments() {
        return payments;
    }
}
