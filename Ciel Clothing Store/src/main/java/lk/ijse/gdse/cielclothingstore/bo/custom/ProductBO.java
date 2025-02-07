package lk.ijse.gdse.cielclothingstore.bo.custom;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dto.ProductDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductBO extends SuperBO {
     String getNextProductId() throws SQLException;
     ArrayList<ProductDto> getAllProducts() throws SQLException;
     boolean deleteProduct(String productID) throws SQLException;
     ArrayList<String> getAllProductIds() throws SQLException;
     ProductDto findById(String selectedProductId) throws SQLException;
     boolean saveProduct(ProductDto productDto) throws SQLException ;
     boolean updateProduct(ProductDto productDto) throws SQLException ;
}
