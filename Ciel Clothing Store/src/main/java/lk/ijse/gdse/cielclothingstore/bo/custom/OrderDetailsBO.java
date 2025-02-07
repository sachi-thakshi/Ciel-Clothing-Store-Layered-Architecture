package lk.ijse.gdse.cielclothingstore.bo.custom;

import lk.ijse.gdse.cielclothingstore.bo.SuperBO;
import lk.ijse.gdse.cielclothingstore.dto.OrderDetailsDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsBO extends SuperBO {
     ArrayList<OrderDetailsDto> getAllOrderDetails() throws SQLException;
}
