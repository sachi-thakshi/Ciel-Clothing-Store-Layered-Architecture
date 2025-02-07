package lk.ijse.gdse.cielclothingstore.bo.custom;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;
import lk.ijse.gdse.cielclothingstore.dto.ProductDetailsDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDetailsBO extends SuperBO {
     ArrayList<ProductDetailsDto> getAllProductDetails() throws SQLException;

}
