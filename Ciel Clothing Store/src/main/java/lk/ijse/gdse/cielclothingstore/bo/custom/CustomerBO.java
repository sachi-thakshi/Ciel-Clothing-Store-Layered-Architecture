package lk.ijse.gdse.cielclothingstore.bo.custom;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
     String getNextCustomerId() throws SQLException;
     ArrayList<CustomerDto> getAllCustomers() throws SQLException;
     boolean deleteCustomer(String CustomerID) throws SQLException;
     boolean saveCustomer(CustomerDto customerDto) throws SQLException;
     boolean updateCustomer(CustomerDto customerDto) throws SQLException;
     ArrayList<String> getAllCustomerIds() throws SQLException;
     CustomerDto findById(String selectedCustomerID) throws SQLException;
}
