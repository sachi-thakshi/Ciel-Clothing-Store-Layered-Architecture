package lk.ijse.gdse.cielclothingstore.bo.custom;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;
import lk.ijse.gdse.cielclothingstore.dto.SuppliersDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
     String getNextSupplierId() throws SQLException;
     ArrayList<SuppliersDto> getAllSuppliers() throws SQLException;
     boolean deleteSupplier(String SupplierID) throws SQLException ;
     boolean saveSupplier(SuppliersDto suppliersDto) throws SQLException;
     boolean updateSupplier(SuppliersDto suppliersDto) throws SQLException;
}
