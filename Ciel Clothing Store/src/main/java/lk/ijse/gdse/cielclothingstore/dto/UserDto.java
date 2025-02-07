package lk.ijse.gdse.cielclothingstore.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private String UserID;
    private String EmployeeID;
    private String Name;
    private String Role;
    private String Password;
    private String ContactNo;
}
