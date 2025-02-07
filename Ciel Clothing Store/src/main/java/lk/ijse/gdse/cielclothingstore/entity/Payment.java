package lk.ijse.gdse.cielclothingstore.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Payment implements Serializable {
    private String PaymentID;
    private String OrderID;
    private double Amount;
    private Date date;
    private Time time;
}
