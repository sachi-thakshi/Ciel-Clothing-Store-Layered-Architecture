package lk.ijse.gdse.cielclothingstore.bo.custom;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;
import lk.ijse.gdse.cielclothingstore.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
     String getNextEmployeeId() throws SQLException;
     ArrayList<EmployeeDto> getAllEmployees() throws SQLException;
     boolean deleteEmployee(String employeeID) throws SQLException;
     boolean saveEmployee(EmployeeDto employeeDto) throws SQLException ;
     boolean updateEmployee(EmployeeDto employeeDto) throws SQLException;
}
