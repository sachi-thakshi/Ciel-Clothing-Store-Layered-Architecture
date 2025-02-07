package lk.ijse.gdse.cielclothingstore.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserAccount implements Serializable {
    private String UserID;
    private String EmployeeID;
    private String Name;
    private String Role;
    private String Password;
    private String ContactNo;
}
