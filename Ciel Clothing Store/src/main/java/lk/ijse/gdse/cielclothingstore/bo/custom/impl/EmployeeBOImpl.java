package lk.ijse.gdse.cielclothingstore.bo.custom.impl;

import lk.ijse.gdse.cielclothingstore.bo.custom.EmployeeBO;
import lk.ijse.gdse.cielclothingstore.dao.DAOFactory;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dao.custom.EmployeeDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dto.EmployeeDto;
import lk.ijse.gdse.cielclothingstore.entity.Customer;
import lk.ijse.gdse.cielclothingstore.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public String getNextEmployeeId() throws SQLException {
        return employeeDAO.getNextId();
    }

    @Override
    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException {
        ArrayList<Employee> employee = employeeDAO.getAll();
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employees: employee) {
            EmployeeDto employeeDto = new EmployeeDto(
                    employees.getEmployeeID(),
                    employees.getName(),
                    employees.getSalary(),
                    employees.getRole(),
                    employees.getContactNo());

            employeeDtos.add(employeeDto);
        }
        return employeeDtos;
    }

    @Override
    public boolean deleteEmployee(String employeeID) throws SQLException {
        return employeeDAO.delete(employeeID);
    }

    @Override
    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException {
        return employeeDAO.save(new Employee(
                employeeDto.getEmployeeID(),
                employeeDto.getName(),
                employeeDto.getSalary(),
                employeeDto.getRole(),
                employeeDto.getContactNo()));
    }

    @Override
    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException {
        return employeeDAO.update(new Employee(
                employeeDto.getEmployeeID(),
                employeeDto.getName(),
                employeeDto.getSalary(),
                employeeDto.getRole(),
                employeeDto.getContactNo()));
    }
}
