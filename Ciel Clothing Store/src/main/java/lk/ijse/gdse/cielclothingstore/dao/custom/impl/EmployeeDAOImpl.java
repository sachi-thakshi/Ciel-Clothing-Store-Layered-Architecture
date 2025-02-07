package lk.ijse.gdse.cielclothingstore.dao.custom.impl;

import lk.ijse.gdse.cielclothingstore.dao.custom.EmployeeDAO;
import lk.ijse.gdse.cielclothingstore.dto.EmployeeDto;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT EmployeeID FROM Employee ORDER BY EmployeeID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);  // Extract the number from the last Employee ID (E001 -> 001)
            int i = Integer.parseInt(substring);  // Convert the number to int
            int newIdIndex = i + 1;  // Increment the ID
            return String.format("E%03d", newIdIndex);  // Format the new ID as E001, E002, etc.
        }
        return "E001"; // Return the default employee ID if no data is found
    }

    @Override
    public boolean save(Employee entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO Employee (EmployeeID, Name, Salary, Role, ContactNo) VALUES (?,?,?,?,?)",
                entity.getEmployeeID(),
                entity.getName(),
                entity.getSalary(),
                entity.getRole(),
                entity.getContactNo()
        );
    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee");
        ArrayList<Employee> employeeDtoArrayList= new ArrayList<>();

        while (rst.next()) {
            employeeDtoArrayList.add(new Employee(
                            rst.getString("EmployeeID"),
                            rst.getString("Name"),
                            rst.getDouble("Salary"),
                            rst.getString("Role"),
                            rst.getString("ContactNo")));
        }
        return employeeDtoArrayList;
    }

    @Override
    public boolean delete(String employeeID) throws SQLException {
        return SQLUtil.execute("DELETE FROM Employee WHERE EmployeeID=?", employeeID);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT EmployeeID FROM Employee");

        ArrayList<String> employeeIds = new ArrayList<>();

        while (rst.next()) {
            employeeIds.add(rst.getString(1));
        }

        return employeeIds;
    }

    @Override
    public Employee findById(String selectedEmployeeId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee WHERE EmployeeID=?", selectedEmployeeId);
        rst.next();
        return new Employee(
                    rst.getString("EmployeeID"),
                    rst.getString("Name"),
                    rst.getDouble("Salary"),
                    rst.getString("Role"),
                    rst.getString("ContactNo"));
    }

    @Override
    public boolean update(Employee entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE Employee SET Name=?, Salary=?, Role=?, ContactNo=? WHERE EmployeeID=?",
                entity.getName(),
                entity.getSalary(),
                entity.getRole(),
                entity.getContactNo(),
                entity.getEmployeeID());
    }
}
