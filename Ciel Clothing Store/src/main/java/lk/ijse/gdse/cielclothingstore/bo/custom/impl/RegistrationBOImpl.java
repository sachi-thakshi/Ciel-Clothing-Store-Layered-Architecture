package lk.ijse.gdse.cielclothingstore.bo.custom.impl;

import lk.ijse.gdse.cielclothingstore.bo.custom.RegistrationBO;
import lk.ijse.gdse.cielclothingstore.dao.DAOFactory;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dao.custom.UserDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.impl.UserDAOImpl;
import lk.ijse.gdse.cielclothingstore.dto.RegistrationDto;
import lk.ijse.gdse.cielclothingstore.dto.UserDto;
import lk.ijse.gdse.cielclothingstore.entity.UserAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationBOImpl implements RegistrationBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USERACCOUNT);

    @Override
    public String getNextUserId() throws SQLException {
        return userDAO.getNextId();
    }

//    @Override
//    public boolean registeredUser(UserAccount entity) throws SQLException {
//        return userDAO.registeredUser(entity);
//    }
}
