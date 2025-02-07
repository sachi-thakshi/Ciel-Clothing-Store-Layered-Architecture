package lk.ijse.gdse.cielclothingstore.bo.custom;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;
import lk.ijse.gdse.cielclothingstore.dto.PaymentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentDetailsBO  extends SuperBO {
     ArrayList<PaymentDto> getAllPaymentDetails() throws SQLException;
}
