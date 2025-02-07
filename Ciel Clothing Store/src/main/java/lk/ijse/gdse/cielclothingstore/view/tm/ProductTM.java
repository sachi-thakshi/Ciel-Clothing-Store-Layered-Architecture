package lk.ijse.gdse.cielclothingstore.view.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductTM {
    private String ProductID;
    private String Name;
    private double Price;
    private String Description;
    private int Quantity;
    private String SupplierID;
}
