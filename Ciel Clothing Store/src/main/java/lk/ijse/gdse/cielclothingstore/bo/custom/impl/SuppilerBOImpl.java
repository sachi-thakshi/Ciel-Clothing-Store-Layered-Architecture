package lk.ijse.gdse.cielclothingstore.bo.custom.impl;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;
import lk.ijse.gdse.cielclothingstore.bo.custom.SupplierBO;
import lk.ijse.gdse.cielclothingstore.dao.DAOFactory;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dao.custom.SupplierDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dto.SuppliersDto;
import lk.ijse.gdse.cielclothingstore.entity.Customer;
import lk.ijse.gdse.cielclothingstore.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SuppilerBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public String getNextSupplierId() throws SQLException {
        return supplierDAO.getNextId();
    }

    @Override
    public ArrayList<SuppliersDto> getAllSuppliers() throws SQLException {
        ArrayList<Supplier> supplier = supplierDAO.getAll();
        ArrayList<SuppliersDto> suppliersDtos = new ArrayList<>();
        for (Supplier suppliers: supplier) {
            SuppliersDto suppliersDto = new SuppliersDto(
                    suppliers.getSupplierID(),
                    suppliers.getName(),
                    suppliers.getContactNo(),
                    suppliers.getAddress());
            suppliersDtos.add(suppliersDto);
        }
        return suppliersDtos;    }

    @Override
    public boolean deleteSupplier(String SupplierID) throws SQLException {
        return supplierDAO.delete(SupplierID);
    }

    @Override
    public boolean saveSupplier(SuppliersDto suppliersDto) throws SQLException {
        return supplierDAO.save(new Supplier(
                suppliersDto.getSupplierID(),
                suppliersDto.getName(),
                suppliersDto.getContactNo(),
                suppliersDto.getAddress()
        ));
    }

    @Override
    public boolean updateSupplier(SuppliersDto suppliersDto) throws SQLException {
        return supplierDAO.update(new Supplier(
                suppliersDto.getSupplierID(),
                suppliersDto.getName(),
                suppliersDto.getContactNo(),
                suppliersDto.getAddress()
        ));    }
}
