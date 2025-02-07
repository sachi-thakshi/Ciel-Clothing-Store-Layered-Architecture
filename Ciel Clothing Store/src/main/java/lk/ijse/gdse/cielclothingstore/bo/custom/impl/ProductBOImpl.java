package lk.ijse.gdse.cielclothingstore.bo.custom.impl;

import lk.ijse.gdse.cielclothingstore.bo.custom.ProductBO;
import lk.ijse.gdse.cielclothingstore.dao.DAOFactory;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dao.custom.ProductDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.impl.ProductDAOImpl;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dto.ProductDto;
import lk.ijse.gdse.cielclothingstore.entity.Customer;
import lk.ijse.gdse.cielclothingstore.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBOImpl implements ProductBO {

    ProductDAO productDAO = (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT);

    @Override
    public String getNextProductId() throws SQLException {
        return productDAO.getNextId();
    }

    @Override
    public ArrayList<ProductDto> getAllProducts() throws SQLException {
        ArrayList<Product> product = productDAO.getAll();
        ArrayList<ProductDto> productDtos = new ArrayList<>();
        for (Product products: product) {
            ProductDto productDto = new ProductDto(
                    products.getProductID(),
                    products.getName(),
                    products.getPrice(),
                    products.getDescription(),
                    products.getQuantity(),
                    products.getSupplierID()
            );
            productDtos.add(productDto);
        }
        return productDtos;    }

    @Override
    public boolean deleteProduct(String productID) throws SQLException {
        return productDAO.delete(productID);
    }

    @Override
    public ArrayList<String> getAllProductIds() throws SQLException {
        return productDAO.getAllIds();
    }

    @Override
    public ProductDto findById(String selectedProductId) throws SQLException{
        Product product = productDAO.findById(selectedProductId);
        return new ProductDto(
                product.getProductID(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getQuantity(),
                product.getSupplierID());    }

    @Override
    public boolean saveProduct(ProductDto productDto) throws SQLException {
        return productDAO.save(new Product(
                productDto.getProductID(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getDescription(),
                productDto.getQuantity(),
                productDto.getSupplierID()
        ));
    }

    @Override
    public boolean updateProduct(ProductDto productDto) throws SQLException {
        return productDAO.update(new Product(
                productDto.getProductID(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getDescription(),
                productDto.getQuantity(),
                productDto.getSupplierID()
        ));
    }
}
