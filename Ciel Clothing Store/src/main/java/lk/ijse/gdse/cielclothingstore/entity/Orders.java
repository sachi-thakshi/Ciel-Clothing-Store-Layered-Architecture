package lk.ijse.gdse.cielclothingstore.entity;

import lk.ijse.gdse.cielclothingstore.dto.OrderDetailsDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Orders implements Serializable {
    private String OrderID;
    private String CustomerID;
    private Date Date;

    private ArrayList<OrderDetails> orderDetailsDtos;

}
