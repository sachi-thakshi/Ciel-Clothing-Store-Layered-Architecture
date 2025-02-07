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
import lk.ijse.gdse.cielclothingstore.bo.custom.OrderDetailsBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.OrderDetailsBOImpl;
import lk.ijse.gdse.cielclothingstore.dto.OrderDetailsDto;
import lk.ijse.gdse.cielclothingstore.view.tm.OrderDetailsTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderdetailsViewController implements Initializable {

    @FXML
    private TableColumn<OrderDetailsTM, String> colOrderId;

    @FXML
    private TableColumn<OrderDetailsTM, Double> colPrice;

    @FXML
    private TableColumn<OrderDetailsTM, String> colProductId;

    @FXML
    private TableColumn<OrderDetailsTM, Integer> colQty;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TableView<OrderDetailsTM> tblOrderDetails;

    private final ObservableList<OrderDetailsTM> orderDetailsList = FXCollections.observableArrayList();

//    private final OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
    private final OrderDetailsBO orderDetailsBO = new OrderDetailsBOImpl();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        loadOrderDetails();

    }

    private void loadOrderDetails() {
        try {
            ArrayList<OrderDetailsDto> orderDetailsDtos = orderDetailsBO.getAllOrderDetails();
            for (OrderDetailsDto dto : orderDetailsDtos) {
                orderDetailsList.add(new OrderDetailsTM(
                        dto.getOrderID(),
                        dto.getProductID(),
                        dto.getQuantity(),
                        dto.getPrice()
                ));
            }
            tblOrderDetails.setItems(orderDetailsList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL errors gracefully, e.g., show an alert
        }
    }

}

