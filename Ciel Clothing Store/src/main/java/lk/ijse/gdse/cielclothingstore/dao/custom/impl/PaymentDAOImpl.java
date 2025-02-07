package lk.ijse.gdse.cielclothingstore.dao.custom.impl;

import lk.ijse.gdse.cielclothingstore.dao.custom.PaymentDAO;
import lk.ijse.gdse.cielclothingstore.dto.PaymentDto;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl  implements PaymentDAO {

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT PaymentID FROM payment ORDER BY PaymentID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    @Override
    public boolean save(Payment entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO payment (PaymentID, OrderID, Amount, Date, Time) VALUES (?,?,?,?,?)",
                entity.getPaymentID(),
                entity.getOrderID(),
                entity.getAmount(),
                entity.getDate(),
                entity.getTime()
        );
    }

    @Override
    public ArrayList<Payment> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Payment");

        ArrayList<Payment> paymentDtoArrayList = new ArrayList<>();

        while (rst.next()) {
            paymentDtoArrayList.add(new Payment(
                    rst.getString("PaymentID"),
                    rst.getString("OrderID"),
                    rst.getDouble("Amount"),
                    rst.getDate("Date"),
                    rst.getTime("Time")));
        }
        return paymentDtoArrayList;
    }

    @Override
    public boolean update(Payment entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return null;
    }

    @Override
    public Payment findById(String selectedID) throws SQLException {
        return null;
    }
}
