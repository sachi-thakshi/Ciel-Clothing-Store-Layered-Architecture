package lk.ijse.gdse.cielclothingstore.bo.custom;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;
import lk.ijse.gdse.cielclothingstore.dto.PaymentDto;
import lk.ijse.gdse.cielclothingstore.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
     UserDto findById(String selectedUserId) throws SQLException;
     String getNextPaymentId() throws SQLException;
     boolean savePayment(PaymentDto paymentDto) throws SQLException ;
}
