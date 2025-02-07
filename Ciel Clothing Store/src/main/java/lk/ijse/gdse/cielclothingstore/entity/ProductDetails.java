package lk.ijse.gdse.cielclothingstore.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ProductDetails implements Serializable {
    private String ProductID;
    private String SupplierID;
}
