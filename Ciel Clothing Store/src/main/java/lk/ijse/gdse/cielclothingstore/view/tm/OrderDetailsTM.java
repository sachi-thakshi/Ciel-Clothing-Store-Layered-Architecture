package lk.ijse.gdse.cielclothingstore.view.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsTM {
    private String OrderID;
    private String ProductID;
    private int Quantity;
    private double Price;
}
