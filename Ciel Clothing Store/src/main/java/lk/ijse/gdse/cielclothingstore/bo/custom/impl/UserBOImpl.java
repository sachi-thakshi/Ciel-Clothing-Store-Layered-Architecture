package lk.ijse.gdse.cielclothingstore.bo.custom.impl;

import lk.ijse.gdse.cielclothingstore.bo.custom.UserBO;
import lk.ijse.gdse.cielclothingstore.dao.DAOFactory;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dao.custom.UserDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.impl.UserDAOImpl;
import lk.ijse.gdse.cielclothingstore.db.DBConnection;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dto.UserDto;
import lk.ijse.gdse.cielclothingstore.entity.Customer;
import lk.ijse.gdse.cielclothingstore.entity.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USERACCOUNT);

    @Override
    public String getNextUserId() throws SQLException {
        return userDAO.getNextId();
    }

    @Override
    public ArrayList<UserDto> getAllUsers() throws SQLException {
        ArrayList<UserAccount> userAccount = userDAO.getAll();
        ArrayList<UserDto> userDtos = new ArrayList<>();
        for (UserAccount userAccounts: userAccount) {
            UserDto userDto = new UserDto(
                    userAccounts.getUserID(),
                    userAccounts.getEmployeeID(),
                    userAccounts.getName(),
                    userAccounts.getRole(),
                    userAccounts.getPassword(),
                    userAccounts.getContactNo()
            );
            userDtos.add(userDto);
        }
        return userDtos;    }

    @Override
    public boolean updateUser(UserDto userDto) throws SQLException {
        return userDAO.update(new UserAccount(
                userDto.getUserID(),
                userDto.getEmployeeID(),
                userDto.getName(),
                userDto.getRole(),
                userDto.getPassword(),
                userDto.getContactNo()
        ));
    }

    @Override
    public ArrayList<String> getAllUserIds() throws SQLException {
        return userDAO.getAllIds();
    }

    @Override
    public UserDto findById(String selectedUserId) throws SQLException {
        UserAccount userAccount = userDAO.findById(selectedUserId);
        return new UserDto(
                userAccount.getUserID(),
                userAccount.getEmployeeID(),
                userAccount.getName(),
                userAccount.getRole(),
                userAccount.getPassword(),
                userAccount.getContactNo());
    }

    @Override
    public ArrayList<UserDto> getCredentials(String name, String password) throws SQLException {
        ArrayList<UserAccount> userAccounts = userDAO.getCredentials(name, password);
        ArrayList<UserDto> userDtos = new ArrayList<>();

        for (UserAccount userAccount : userAccounts) {
            UserDto userDto = new UserDto(
                    userAccount.getUserID(),
                    userAccount.getEmployeeID(),
                    userAccount.getName(),
                    userAccount.getRole(),
                    userAccount.getPassword(),
                    userAccount.getContactNo()
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public boolean registeredUser(UserDto userDto) throws SQLException {
        return userDAO.registeredUser(new UserAccount(
                userDto.getUserID(),
                userDto.getEmployeeID(),
                userDto.getName(),
                userDto.getRole(),
                userDto.getPassword(),
                userDto.getContactNo()
        ));
    }
}
