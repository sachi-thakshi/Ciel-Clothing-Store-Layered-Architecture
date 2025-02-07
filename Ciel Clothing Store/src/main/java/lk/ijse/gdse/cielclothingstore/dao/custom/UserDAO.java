package lk.ijse.gdse.cielclothingstore.dao.custom;

import lk.ijse.gdse.cielclothingstore.dao.CrudDAO;
import lk.ijse.gdse.cielclothingstore.dto.UserDto;
import lk.ijse.gdse.cielclothingstore.entity.UserAccount;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends CrudDAO<UserAccount> {
//      String getNextUserId() throws SQLException;
//      boolean saveUser(UserDto userDto) throws SQLException ;
//      ArrayList<UserDto> getAllUsers() throws SQLException ;
//      boolean updateUser(UserDto userDto) throws SQLException ;
//      boolean deleteUser(String UserID) throws SQLException ;
//      ArrayList<String> getAllUserIds() throws SQLException ;
//      UserDto findById(String selectedUserId) throws SQLException;
//      boolean isUsernameExists(String username) throws SQLException ;
//      boolean updatePassword(String username, String newPassword) throws SQLException ;
//      String getNextId() throws SQLException;
//      boolean registeredUser(UserDto userDto) throws SQLException;
//      ArrayList<UserDto> getCredentials(String Name, String Password) throws SQLException ;

     boolean isUsernameExists(String username) throws SQLException ;
     boolean updatePassword(String username, String newPassword) throws SQLException ;
     boolean registeredUser(UserAccount entity) throws SQLException;
     ArrayList<UserAccount> getCredentials(String Name, String Password) throws SQLException ;

}
