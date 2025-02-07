package lk.ijse.gdse.cielclothingstore.dao.custom.impl;

import lk.ijse.gdse.cielclothingstore.dao.custom.ProductDAO;
import lk.ijse.gdse.cielclothingstore.dto.ProductDto;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT ProductID FROM product ORDER BY ProductID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    @Override
    public boolean save(Product entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO product (ProductID, Name, Price, Description, Quantity, SupplierID) VALUES (?,?,?,?,?,?)",
                entity.getProductID(),
                entity.getName(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getQuantity(),
                entity.getSupplierID());
    }

    @Override
    public boolean update(Product entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE product SET Name = ?, Price = ?, Description = ?, Quantity = ?, SupplierID = ? WHERE ProductID = ?",
                entity.getName(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getQuantity(),
                entity.getSupplierID(),
                entity.getProductID()
        );
    }

    @Override
    public ArrayList<Product> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM product");

        ArrayList<Product> productDtoArrayList= new ArrayList<>();

        while (rst.next()) {
            productDtoArrayList.add(new Product(
                    rst.getString("ProductID"),
                    rst.getString("Name"),
                    rst.getDouble("Price"),
                    rst.getString("Description"),
                    rst.getInt("Quantity"),
                    rst.getString("SupplierID")));
        }
        return productDtoArrayList;
    }

    @Override
    public boolean delete(String productID) throws SQLException {
        return SQLUtil.execute("DELETE FROM product WHERE ProductID=?", productID);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT ProductID FROM product");

        ArrayList<String> productIds = new ArrayList<>();

        while (rst.next()) {
            productIds.add(rst.getString(1));
        }
        return productIds;
    }

    @Override
    public Product findById(String selectedProductId) throws SQLException{
        ResultSet rst = SQLUtil.execute("SELECT * FROM product WHERE ProductID =?" ,  selectedProductId );
        rst.next();
            return new Product(
                    rst.getString("ProductID"),
                    rst.getString("Name"),
                    rst.getDouble("Price"),
                    rst.getString("Description"),
                    rst.getInt("Quantity"),
                    rst.getString("SupplierID"));
    }

    @Override
    public boolean reduceQty(String productID, int quantity) throws SQLException {
        // Reduce the quantity of the product in the Product table
        boolean isUpdated = SQLUtil.execute(
                "UPDATE Product SET Quantity = Quantity - ? WHERE ProductID = ?",
                quantity,  // Decrease the product quantity by the given value
                productID  // The product to update
        );

        return isUpdated; // Return whether the update was successful
    }
}
