package lk.ijse.gdse.cielclothingstore.bo.custom.impl;

import lk.ijse.gdse.cielclothingstore.bo.custom.ProductDetailsBO;
import lk.ijse.gdse.cielclothingstore.dao.DAOFactory;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dao.custom.ProductDetailsDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.impl.ProductDetailsDAOImpl;
import lk.ijse.gdse.cielclothingstore.dto.PaymentDto;
import lk.ijse.gdse.cielclothingstore.dto.ProductDetailsDto;
import lk.ijse.gdse.cielclothingstore.entity.Payment;
import lk.ijse.gdse.cielclothingstore.entity.ProductDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDetailsBOImpl implements ProductDetailsBO {

    ProductDetailsDAO productDetailsDAO = (ProductDetailsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT_DETAILS);

    @Override
    public ArrayList<ProductDetailsDto> getAllProductDetails() throws SQLException {
        ArrayList<ProductDetails> productDetails = productDetailsDAO.getAll();
        ArrayList<ProductDetailsDto> productDetailsDtos = new ArrayList<>();
        for (ProductDetails productDetail: productDetails) {
            ProductDetailsDto productDetailsDto = new ProductDetailsDto(
                    productDetail.getProductID(),
                    productDetail.getSupplierID());
            productDetailsDtos.add(productDetailsDto);
        }
        return productDetailsDtos;
    }
}
