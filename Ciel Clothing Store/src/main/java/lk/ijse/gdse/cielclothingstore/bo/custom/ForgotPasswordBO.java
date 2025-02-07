package lk.ijse.gdse.cielclothingstore.bo.custom;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;

import java.sql.SQLException;

public interface ForgotPasswordBO extends SuperBO {

     boolean isUsernameExists(String username) throws SQLException ;
     boolean updatePassword(String username, String newPassword) throws SQLException ;
}
