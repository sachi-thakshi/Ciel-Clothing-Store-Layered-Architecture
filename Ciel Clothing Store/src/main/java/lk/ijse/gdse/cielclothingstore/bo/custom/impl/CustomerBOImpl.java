package lk.ijse.gdse.cielclothingstore.bo.custom.impl;

import lk.ijse.gdse.cielclothingstore.bo.custom.CustomerBO;
import lk.ijse.gdse.cielclothingstore.dao.DAOFactory;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dao.custom.CustomerDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public String getNextCustomerId() throws SQLException {
        return customerDAO.getNextId();
    }

    @Override
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException{
        ArrayList<Customer> customer = customerDAO.getAll();
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customers: customer) {
            CustomerDto customerDto = new CustomerDto(
                    customers.getCustomerID(),
                    customers.getUserID(),
                    customers.getName(),
                    customers.getPriority(),
                    customers.getContactNo(),
                    customers.getEmail()
            );
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }

    @Override
    public boolean deleteCustomer(String CustomerID) throws SQLException {
        return customerDAO.delete(CustomerID);
    }

    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException {
        return customerDAO.save(new Customer(
                customerDto.getCustomerID(),
                customerDto.getUserID(),
                customerDto.getName(),
                customerDto.getPriority(),
                customerDto.getContactNo(),
                customerDto.getEmail()));
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        return customerDAO.update(new Customer(
                customerDto.getCustomerID(),
                customerDto.getUserID(),
                customerDto.getName(),
                customerDto.getPriority(),
                customerDto.getContactNo(),
                customerDto.getEmail()));
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException {
        return customerDAO.getAllIds();
    }

    @Override
    public CustomerDto findById(String selectedCustomerID) throws SQLException {
        Customer customer = customerDAO.findById(selectedCustomerID);
        return new CustomerDto(
                customer.getCustomerID(),
                customer.getUserID(),
                customer.getName(),
                customer.getPriority(),
                customer.getContactNo(),
                customer.getEmail());
    }
}
