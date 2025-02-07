package lk.ijse.gdse.cielclothingstore.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Product implements Serializable {
    private String ProductID;
    private String Name;
    private double Price;
    private String Description;
    private int Quantity;
    private String SupplierID;
}
