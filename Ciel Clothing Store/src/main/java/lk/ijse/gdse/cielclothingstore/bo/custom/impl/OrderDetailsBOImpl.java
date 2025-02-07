package lk.ijse.gdse.cielclothingstore.bo.custom.impl;

import lk.ijse.gdse.cielclothingstore.bo.custom.OrderDetailsBO;
import lk.ijse.gdse.cielclothingstore.dao.DAOFactory;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.cielclothingstore.dao.custom.impl.OrderDetailsDAOImpl;
import lk.ijse.gdse.cielclothingstore.dto.CustomerDto;
import lk.ijse.gdse.cielclothingstore.dto.OrderDetailsDto;
import lk.ijse.gdse.cielclothingstore.entity.Customer;
import lk.ijse.gdse.cielclothingstore.entity.OrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsBOImpl implements OrderDetailsBO {

    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAILS);

    @Override
    public ArrayList<OrderDetailsDto> getAllOrderDetails() throws SQLException {
        ArrayList<OrderDetails> orderDetails = orderDetailsDAO.getAll();
        ArrayList<OrderDetailsDto> orderDetailsDtos = new ArrayList<>();
        for (OrderDetails orderDetail: orderDetails) {
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                    orderDetail.getOrderID(),
                    orderDetail.getProductID(),
                    orderDetail.getQuantity(),
                    orderDetail.getPrice());
            orderDetailsDtos.add(orderDetailsDto);
        }
        return orderDetailsDtos;
    }
}
