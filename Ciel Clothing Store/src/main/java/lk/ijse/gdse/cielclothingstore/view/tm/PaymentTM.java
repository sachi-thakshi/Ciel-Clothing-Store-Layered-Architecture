package lk.ijse.gdse.cielclothingstore.view.tm;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTM {
    private String paymentID;
    private String orderID;
    private Date paymentDate;
    private double amount;
    private String method;
}