package lk.ijse.gdse.cielclothingstore.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String ProductID;
    private String Name;
    private double Price;
    private String Description;
    private int Quantity;
    private String SupplierID;
}