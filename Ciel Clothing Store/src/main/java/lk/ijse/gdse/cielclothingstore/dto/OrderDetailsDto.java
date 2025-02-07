package lk.ijse.gdse.cielclothingstore.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    private String OrderID;
    private String ProductID;
    private int Quantity;
    private double Price;
}
