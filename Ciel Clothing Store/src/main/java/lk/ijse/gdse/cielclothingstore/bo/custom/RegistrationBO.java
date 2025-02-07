package lk.ijse.gdse.cielclothingstore.bo.custom;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;
import lk.ijse.gdse.cielclothingstore.dto.UserDto;
import lk.ijse.gdse.cielclothingstore.entity.UserAccount;

import java.sql.SQLException;

public interface RegistrationBO extends SuperBO {
     String getNextUserId() throws SQLException ;
//     boolean registeredUser(UserAccount entity) throws SQLException ;
}
