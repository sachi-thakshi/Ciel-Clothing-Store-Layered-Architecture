package lk.ijse.gdse.cielclothingstore.bo.custom.impl;

import lk.ijse.gdse.cielclothingstore.bo.custom.PaymentBO;
import lk.ijse.gdse.cielclothingstore.dao.DAOFactory;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dao.custom.PaymentDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.UserDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.impl.UserDAOImpl;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dto.PaymentDto;
import lk.ijse.gdse.cielclothingstore.dto.UserDto;
import lk.ijse.gdse.cielclothingstore.entity.Customer;
import lk.ijse.gdse.cielclothingstore.entity.Payment;
import lk.ijse.gdse.cielclothingstore.entity.UserAccount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USERACCOUNT);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public UserDto findById(String selectedUserId) throws SQLException {
        UserAccount userAccount = userDAO.findById(selectedUserId);
        return new UserDto(
                userAccount.getUserID(),
                userAccount.getEmployeeID(),
                userAccount.getName(),
                userAccount.getRole(),
                userAccount.getPassword(),
                userAccount.getContactNo());    }

    @Override
    public String getNextPaymentId() throws SQLException {
        return paymentDAO.getNextId();
    }

    @Override
    public boolean savePayment(PaymentDto paymentDto) throws SQLException {
        return paymentDAO.save(new Payment(
                paymentDto.getPaymentID(),
                paymentDto.getOrderID(),
                paymentDto.getAmount(),
                paymentDto.getDate(),
                paymentDto.getTime()));
    }
}
