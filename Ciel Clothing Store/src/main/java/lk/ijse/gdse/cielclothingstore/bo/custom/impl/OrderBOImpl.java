package lk.ijse.gdse.cielclothingstore.bo.custom.impl;

import lk.ijse.gdse.cielclothingstore.bo.custom.OrderBO;
import lk.ijse.gdse.cielclothingstore.dao.DAOFactory;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dao.custom.CustomerDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.OrderDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.ProductDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.impl.OrderDAOImpl;
import lk.ijse.gdse.cielclothingstore.db.DBConnection;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dto.OrderDetailsDto;
import lk.ijse.gdse.cielclothingstore.dto.OrderDto;
import lk.ijse.gdse.cielclothingstore.dto.ProductDto;
import lk.ijse.gdse.cielclothingstore.entity.Customer;
import lk.ijse.gdse.cielclothingstore.entity.OrderDetails;
import lk.ijse.gdse.cielclothingstore.entity.Orders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERS);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAILS);
    OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
    @Override
    public String getNextOrderId() throws SQLException {
        return orderDAO.getNextId();
    }

    @Override
    public boolean saveOrder(OrderDto orderDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false); // 1

            if (orderDto.getDate() == null) {
                throw new SQLException("Order date cannot be null.");
            }

            java.util.Date utilDate = orderDto.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            boolean isOrderSaved = SQLUtil.execute(
                    "INSERT INTO orders (OrderID, CustomerID, Date) VALUES (?, ?, ?)",
                    orderDto.getOrderID(),
                    orderDto.getCustomerID(),
                    orderDto.getDate()
            );

            if (isOrderSaved) {
                ArrayList<OrderDetailsDto> orderDetailsDtos = orderDto.getOrderDetailsDtos();
                ArrayList<OrderDetails> orderDetails = new ArrayList<>();
                for(OrderDetailsDto orderDetail : orderDetailsDtos) {
                    orderDetails.add(new OrderDetails(orderDetail.getOrderID(),orderDetail.getProductID(),orderDetail.getQuantity(),orderDetail.getPrice()));
                }
                boolean isOrderDetailListSaved = orderDetailsDAO.SaveOrderDetailsList(orderDetails);

                if (isOrderDetailListSaved) {
                    connection.commit(); // 2
                    return true;
                }
            }

            connection.rollback(); // 3
            return false;
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            // Catch all other exceptions and rollback the transaction
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally {
            // Reset auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }


//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//            if (orderDto.getDate() == null) {
//                throw new SQLException("Order date cannot be null.");
//            }
//
//            java.util.Date utilDate = orderDto.getDate();
//            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//
//            boolean isOrderSaved = CrudUtil.execute(
//                    "INSERT INTO orders (OrderID, CustomerID, Date) VALUES (?, ?, ?)",
//                    orderDto.getOrderID(),
//                    orderDto.getCustomerID(),
//                    sqlDate             );
//
//            if (isOrderSaved) {
//                boolean isOrderDetailListSaved = orderDetailsModel.SaveOrderDetailsList(orderDto.getOrderDetailsDtos());
//
//                if (isOrderDetailListSaved) {
//                    connection.commit();
//                    return true;
//                }
//            }
//
//            connection.rollback();
//            return false;
//        } catch (SQLException e) {
//            connection.rollback();
//            e.printStackTrace();
//            return false;
//        } catch (Exception e) {
//            connection.rollback();
//            e.printStackTrace();
//            return false;
//        } finally {
//            connection.setAutoCommit(true);
//        }

    }

    @Override
    public CustomerDto findById(String selectedCustomerID) throws SQLException {
        Customer customer = customerDAO.findById(selectedCustomerID);
        return new CustomerDto(
                customer.getCustomerID(),
                customer.getUserID(),
                customer.getContactNo(),
                customer.getPriority(),
                customer.getContactNo(),
                customer.getEmail());
    }

}
