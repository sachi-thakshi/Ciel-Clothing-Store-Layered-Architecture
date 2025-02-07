package lk.ijse.gdse.cielclothingstore.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Customer implements Serializable {
    private String CustomerID;
    private String UserID;
    private String Name;
    private String Priority;
    private String ContactNo;
    private String Email;
}
