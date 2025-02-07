package lk.ijse.gdse.cielclothingstore.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDto {
    private String PaymentID;
    private String OrderID;
    private Double Amount;
    private Date Date;
    private Time Time;

}
