package lk.ijse.gdse.cielclothingstore.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.cielclothingstore.bo.BOFactory;
import lk.ijse.gdse.cielclothingstore.bo.custom.PaymentBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.PaymentDetailsBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.PaymentBOImpl;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.PaymentDetailsBOImpl;
import lk.ijse.gdse.cielclothingstore.dto.PaymentDto;
import lk.ijse.gdse.cielclothingstore.view.tm.PaymentDetailsTM;


import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentDetailsViewController implements Initializable {

    @FXML
    private TableColumn<PaymentDetailsTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentDetailsTM, String> colDate;

    @FXML
    private TableColumn<PaymentDetailsTM, String> colOrderId;

    @FXML
    private TableColumn<PaymentDetailsTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentDetailsTM, String> colTime;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TableView<PaymentDetailsTM> tbtPaymentDetails;

//    private final PaymentModel paymentModel = new PaymentModel();
    private final PaymentDetailsBO paymentDetailsBO = new PaymentDetailsBOImpl();

    private final ObservableList<PaymentDetailsTM> paymentDetailsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("PaymentID"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("PaymentDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("PaymentTime"));

        loadPaymentDetails();
    }

    private void loadPaymentDetails() {
        try {
            ArrayList<PaymentDto> paymentDtos = paymentDetailsBO.getAllPaymentDetails();
            for (PaymentDto dto : paymentDtos) {
                // Format the Date and Time using SimpleDateFormat
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dto.getDate() != null ? dateFormat.format(dto.getDate()) : "";

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String formattedTime = dto.getTime() != null ? timeFormat.format(dto.getTime()) : "";

                // Add the formatted values to the PaymentDetailsTM list
                paymentDetailsList.add(new PaymentDetailsTM(
                        dto.getPaymentID(),
                        dto.getOrderID(),
                        dto.getAmount(),
                        formattedDate,  // Use formatted date string
                        formattedTime   // Use formatted time string
                ));
            }
            tbtPaymentDetails.setItems(paymentDetailsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

