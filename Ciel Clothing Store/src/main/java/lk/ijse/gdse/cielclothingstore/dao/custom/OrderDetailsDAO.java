package lk.ijse.gdse.cielclothingstore.dao.custom;

import lk.ijse.gdse.cielclothingstore.dao.CrudDAO;
import lk.ijse.gdse.cielclothingstore.dto.OrderDetailsDto;
import lk.ijse.gdse.cielclothingstore.entity.OrderDetails;
import lk.ijse.gdse.cielclothingstore.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO extends CrudDAO<OrderDetails> {
//     ArrayList<OrderDetailsDto> getAllOrderDetails() throws SQLException ;
     boolean SaveOrderDetailsList(ArrayList<OrderDetails> orderDetailsDtoArrayLsit) throws SQLException;
//     boolean saveOrderDetail(OrderDetailsDto orderDetailsDto) throws SQLException;
}
