package lk.ijse.gdse.cielclothingstore.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OrderDetails implements Serializable {
    private String OrderID;
    private String ProductID;
    private int Quantity;
    private double Price;
}
