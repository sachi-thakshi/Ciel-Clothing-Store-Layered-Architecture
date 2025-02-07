package lk.ijse.gdse.cielclothingstore.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Supplier implements Serializable {
    private String SupplierID;
    private String Name;
    private String ContactNo;
    private String Address;
}
