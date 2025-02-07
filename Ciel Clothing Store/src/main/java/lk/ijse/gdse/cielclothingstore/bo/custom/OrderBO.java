package lk.ijse.gdse.cielclothingstore.bo.custom;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dto.OrderDto;
import lk.ijse.gdse.cielclothingstore.dto.ProductDto;
import lk.ijse.gdse.cielclothingstore.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
     String getNextOrderId() throws SQLException ;
     boolean saveOrder(OrderDto orderdto) throws SQLException;
     CustomerDto findById(String selectedCustomerID) throws SQLException;
}
