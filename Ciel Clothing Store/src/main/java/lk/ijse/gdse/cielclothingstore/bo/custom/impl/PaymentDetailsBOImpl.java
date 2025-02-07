package lk.ijse.gdse.cielclothingstore.bo.custom.impl;

import lk.ijse.gdse.cielclothingstore.bo.custom.PaymentDetailsBO;
import lk.ijse.gdse.cielclothingstore.dao.DAOFactory;
import lk.ijse.gdse.cielclothingstore.dao.custom.PaymentDAO;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dto.PaymentDto;
import lk.ijse.gdse.cielclothingstore.entity.Customer;
import lk.ijse.gdse.cielclothingstore.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDetailsBOImpl implements PaymentDetailsBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public ArrayList<PaymentDto> getAllPaymentDetails() throws SQLException {
        ArrayList<Payment> payment = paymentDAO.getAll();
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
        for (Payment payments: payment) {
            PaymentDto paymentDto = new PaymentDto(
                    payments.getPaymentID(),
                    payments.getOrderID(),
                    payments.getAmount(),
                    payments.getDate(),
                    payments.getTime());
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }
}
