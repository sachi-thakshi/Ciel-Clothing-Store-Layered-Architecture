package lk.ijse.gdse.cielclothingstore.dao.custom.impl;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import lk.ijse.gdse.cielclothingstore.bo.custom.OrderDetailsBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.impl.OrderDetailsBOImpl;
import lk.ijse.gdse.cielclothingstore.dao.custom.OrderDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.cielclothingstore.db.DBConnection;
import lk.ijse.gdse.cielclothingstore.dto.OrderDto;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.entity.Orders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

//    private final OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
    private final OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAOImpl();

    @Override
    public String getNextId() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT OrderID FROM orders ORDER BY OrderID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // e.g., "OR001"
            String substring = lastId.substring(2); // "001"
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("OR%03d", newIdIndex); // Generates next ID (e.g., "OR002")
        }
        return "OR001"; // Default ID if no orders exist yet
    }

    @Override
    public boolean save(Orders dto) throws SQLException {
        return false;
    }

//    @Override
//    public boolean save(Orders entity) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            // Disabling auto-commit to manually control the transaction
//            connection.setAutoCommit(false); // 1
//
////            // Ensure orderDto.getDate() is not null
////            if (entity.getDate() == null) {
////                throw new SQLException("Order date cannot be null.");
////            }
//
//            // Convert java.util.Date to java.sql.Date
////            java.util.Date utilDate = entity.getDate();
////            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//
//            // Save the order into the orders table
//            boolean isOrderSaved = SQLUtil.execute(
//                    "INSERT INTO orders (OrderID, CustomerID, Date) VALUES (?, ?, ?)",
//                    entity.getOrderID(),
//                    entity.getCustomerID(),
//                    entity.getDate() // Passing the sqlDate object
//            );
//
//            if (isOrderSaved) {
//                // Save the order details into the order_details table
//                boolean isOrderDetailListSaved = orderDetailsDAO.SaveOrderDetailsList(entity.getOrderDetailsDtos());
//
//                if (isOrderDetailListSaved) {
//                    // If both the order and details are saved, commit the transaction
//                    connection.commit(); // 2
//                    return true;
//                }
//            }
//
//            // If saving the order details fails, rollback the transaction
//            connection.rollback(); // 3
//            return false;
//        } catch (SQLException e) {
//            // Catch any exceptions and rollback the transaction in case of failure
//            connection.rollback();
//            e.printStackTrace(); // You can log this exception to a file or logging system for better debugging
//            return false;
//        } catch (Exception e) {
//            // Catch all other exceptions and rollback the transaction
//            connection.rollback();
//            e.printStackTrace(); // You can log this exception to a file or logging system for better debugging
//            return false;
//        } finally {
//            // Reset auto-commit to true after the operation
//            connection.setAutoCommit(true); // 4
//        }
//    }

    @Override
    public ArrayList<Orders> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(Orders entity) throws SQLException {
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
    public Orders findById(String selectedID) throws SQLException {
        return null;
    }
}
