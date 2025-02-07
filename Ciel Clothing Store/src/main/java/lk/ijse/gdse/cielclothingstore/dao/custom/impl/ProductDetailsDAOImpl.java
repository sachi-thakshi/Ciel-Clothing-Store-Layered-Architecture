package lk.ijse.gdse.cielclothingstore.dao.custom.impl;

import lk.ijse.gdse.cielclothingstore.dao.custom.ProductDetailsDAO;
import lk.ijse.gdse.cielclothingstore.dto.ProductDetailsDto;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.entity.ProductDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDetailsDAOImpl implements ProductDetailsDAO {

    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public boolean save(ProductDetails entity) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<ProductDetails> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute( "SELECT ProductID, SupplierID FROM Product");
        ArrayList<ProductDetails> productDetailsDtoArrayList = new ArrayList<>();
        while (rst.next()) {
            productDetailsDtoArrayList.add(new ProductDetails(
                    rst.getString("ProductID"),
                    rst.getString("SupplierID")));
        }
        return productDetailsDtoArrayList;
    }

    @Override
    public boolean update(ProductDetails entity) throws SQLException {
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
    public ProductDetails findById(String selectedID) throws SQLException {
        return null;
    }
}
