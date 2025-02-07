package lk.ijse.gdse.cielclothingstore.dao.custom.impl;

import lk.ijse.gdse.cielclothingstore.dao.custom.CustomerDAO;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public String getNextId() throws SQLException {
            ResultSet rst = SQLUtil.execute("SELECT CustomerID FROM customer ORDER BY CustomerID DESC LIMIT 1");

            if (rst.next()) {
                String lastId = rst.getString(1);
                String substring = lastId.substring( 1);
                int i = Integer.parseInt(substring);
                int newIdIndex = i + 1;
                return String.format("C%03d",newIdIndex);
            }
            return "C001";
        }

        @Override
        public boolean save(Customer entity) throws SQLException {
             return SQLUtil.execute("INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?)",
                    entity.getCustomerID(),
                    entity.getUserID(),
                    entity.getName(),
                    entity.getPriority(),
                    entity.getContactNo(),
                    entity.getEmail());
        }

        @Override
        public ArrayList<Customer> getAll() throws SQLException {
            ResultSet rst = SQLUtil.execute("SELECT * FROM customer");

            ArrayList<Customer> customerDtoArrayList = new ArrayList<>();

            while (rst.next()) {
               customerDtoArrayList.add(new Customer(
                       rst.getString("CustomerID"),
                       rst.getString("UserID"),
                       rst.getString("Name"),
                       rst.getString("Priority"),
                       rst.getString("ContactNo"),
                       rst.getString("Email")));
            }
            return customerDtoArrayList;
        }

        @Override
        public boolean update(Customer entity) throws SQLException {
             return SQLUtil.execute(
                    "UPDATE  customer SET UserID=?, Name=?, Priority=?, ContactNo=?, Email=? WHERE CustomerID=? ",
                    entity.getUserID(),
                    entity.getName(),
                    entity.getPriority(),
                    entity.getContactNo(),
                    entity.getEmail(),
                    entity.getCustomerID());
        }

        @Override
        public boolean delete(String CustomerID) throws SQLException {
             return SQLUtil.execute("DELETE FROM customer WHERE CustomerID=?", CustomerID);
        }


        @Override
        public ArrayList<String> getAllIds() throws SQLException {
            ArrayList<String> customerIds = new ArrayList<>();
            ResultSet rst = SQLUtil.execute("SELECT CustomerID FROM customer");
            while (rst.next()) {
                customerIds.add(rst.getString(1));
            }
            return customerIds;
        }

        @Override
        public Customer findById(String selectedCustomerID) throws SQLException {
            ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE CustomerID=?", selectedCustomerID);
            rst.next();
            return new Customer(
                    rst.getString("CustomerID"),
                    rst.getString("UserID"),
                    rst.getString("Name"),
                    rst.getString("Priority"),
                    rst.getString("ContactNo"),
                    rst.getString("Email"));
        }
}
