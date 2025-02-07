package lk.ijse.gdse.cielclothingstore.dao.custom.impl;

import lk.ijse.gdse.cielclothingstore.dao.custom.SupplierDAO;
import lk.ijse.gdse.cielclothingstore.dto.SuppliersDto;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT SupplierID FROM Supplier ORDER BY SupplierID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring( 1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d",newIdIndex);
        }
        return "S001";
    }

    @Override
    public boolean save(Supplier entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO Supplier VALUES (?, ?, ?, ?)",
                entity.getSupplierID(),
                entity.getName(),
                entity.getContactNo(),
                entity.getAddress()
        );
    }

    @Override
    public ArrayList<Supplier> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier");
        ArrayList<Supplier> suppliersDtoArrayList= new ArrayList<>();
        while (rst.next()) {
            suppliersDtoArrayList.add(new Supplier(
                    rst.getString("SupplierID"),
                    rst.getString("Name"),
                    rst.getString("ContactNo"),
                    rst.getString("Address")));
        }
        return suppliersDtoArrayList;
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE  Supplier SET Name=?, ContactNo=?, Address=? WHERE SupplierID=? ",
                entity.getName(),
                entity.getContactNo(),
                entity.getAddress(),
                entity.getSupplierID()

        );
    }

    @Override
    public boolean delete(String SupplierID) throws SQLException {
        return SQLUtil.execute("DELETE FROM Supplier WHERE SupplierID=?", SupplierID);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT SupplierID FROM Supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }
        return supplierIds;
    }

    @Override
    public Supplier findById(String selectedSupplierID) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier WHERE SupplierID=?", selectedSupplierID);
        rst.next();
        return new Supplier(
                    rst.getString("SupplierID"),
                    rst.getString("Name"),
                    rst.getString("ContactNo"),
                    rst.getString("Address"));
    }
}
