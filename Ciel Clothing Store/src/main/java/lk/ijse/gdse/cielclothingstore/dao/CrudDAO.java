package lk.ijse.gdse.cielclothingstore.dao;

import lk.ijse.gdse.cielclothingstore.dto.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO{
    String getNextId() throws SQLException;
    boolean save(T dto) throws SQLException;
    ArrayList<T> getAll() throws SQLException;
    boolean update(T dto) throws SQLException;
    boolean delete(String id) throws SQLException ;
    ArrayList<String> getAllIds() throws SQLException ;
    T findById(String selectedID) throws SQLException;

}
