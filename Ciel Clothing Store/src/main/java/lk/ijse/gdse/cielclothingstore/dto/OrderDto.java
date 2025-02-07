package lk.ijse.gdse.cielclothingstore.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
    private String OrderID;
    private String CustomerID;
    private Date Date;

    private ArrayList<OrderDetailsDto> orderDetailsDtos;

}
