package lk.ijse.gdse.cielclothingstore.dao.custom.impl;

import lk.ijse.gdse.cielclothingstore.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.ProductDAO;
import lk.ijse.gdse.cielclothingstore.dto.OrderDetailsDto;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.entity.OrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM OrderDetails");

        ArrayList<OrderDetails> orderDetailsDtoArrayList = new ArrayList<>();

        while (rst.next()) {
           orderDetailsDtoArrayList.add(new OrderDetails(
                   rst.getString("OrderID"),
                   rst.getString("ProductID"),
                   rst.getInt("Quantity"),
                   rst.getDouble("Price")));
        }
        return orderDetailsDtoArrayList;
    }

    @Override
    public boolean update(OrderDetails entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    @Override
    public OrderDetails findById(String selectedID) throws SQLException {
        return null;
    }

    @Override
    public boolean SaveOrderDetailsList(ArrayList<OrderDetails> orderDetailsDtoArrayList) throws SQLException {
        for (OrderDetails orderDetail : orderDetailsDtoArrayList) {
            // Save the order details into the OrderDetails table
            System.out.println(orderDetail.getOrderID() + orderDetail.getProductID());
            boolean isDetailSaved = SQLUtil.execute(
                    "INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)",
                    orderDetail.getOrderID(),
                    orderDetail.getProductID(),
                    orderDetail.getQuantity(),
                    orderDetail.getPrice()
            );

            if (!isDetailSaved) {
                return false; // If any detail fails to save, return false
            }

            // Update the product quantity after order details have been saved
            boolean isProductUpdated = productDAO.reduceQty(orderDetail.getProductID(), orderDetail.getQuantity());
            if (!isProductUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        }
        return true; // All details saved successfully
    }


    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(OrderDetails orderDetails) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO OrderDetails VALUES(?,?,?,?)",
                orderDetails.getOrderID(),
                orderDetails.getProductID(),
                orderDetails.getQuantity(),
                orderDetails.getPrice());
    }
}
