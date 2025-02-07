package lk.ijse.gdse.cielclothingstore.view.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailsTM {
    private String PaymentID;
    private String OrderID;
    private Double Amount;
    private String PaymentDate;
    private String PaymentTime;
}