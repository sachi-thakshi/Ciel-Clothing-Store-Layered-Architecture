package lk.ijse.gdse.cielclothingstore.dao.custom;

import lk.ijse.gdse.cielclothingstore.dao.CrudDAO;
import lk.ijse.gdse.cielclothingstore.dto.ProductDto;
import lk.ijse.gdse.cielclothingstore.entity.Product;

import java.sql.SQLException;

public interface ProductDAO extends CrudDAO<Product> {
//     String getNextProductId() throws SQLException;
//     boolean saveProduct(ProductDto productDto) throws SQLException;
//     boolean updateProduct(ProductDto productDto) throws SQLException;
//     ArrayList<ProductDto> getAllProducts() throws SQLException ;
//     boolean deleteProduct(String productID) throws SQLException ;
//     ArrayList<String> getAllProductIds() throws SQLException ;
//     ProductDto findById(String selectedProductId) throws SQLException;
     boolean reduceQty(String productID, int quantity) throws SQLException ;
}
