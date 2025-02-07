package lk.ijse.gdse.cielclothingstore.bo.custom.impl;

import lk.ijse.gdse.cielclothingstore.bo.custom.ForgotPasswordBO;
import lk.ijse.gdse.cielclothingstore.dao.DAOFactory;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dao.custom.UserDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.impl.UserDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPasswordBOImpl implements ForgotPasswordBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USERACCOUNT);

    @Override
    public boolean isUsernameExists(String username) throws SQLException {
        return userDAO.isUsernameExists(username);
    }

    @Override
    public boolean updatePassword(String username, String newPassword) throws SQLException {
        return userDAO.updatePassword(username, newPassword);
    }
}
