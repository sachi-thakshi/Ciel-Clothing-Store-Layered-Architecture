package lk.ijse.gdse.cielclothingstore.bo.custom;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.db.DBConnection;
import lk.ijse.gdse.cielclothingstore.dto.UserDto;
import lk.ijse.gdse.cielclothingstore.entity.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
     String getNextUserId() throws SQLException;
     ArrayList<UserDto> getAllUsers() throws SQLException;
     boolean updateUser(UserDto userDto) throws SQLException ;
     ArrayList<String> getAllUserIds() throws SQLException;
     UserDto findById(String selectedUserId) throws SQLException;
     ArrayList<UserDto> getCredentials(String Name, String Password) throws SQLException;
     boolean registeredUser(UserDto userDto) throws SQLException ;
}
