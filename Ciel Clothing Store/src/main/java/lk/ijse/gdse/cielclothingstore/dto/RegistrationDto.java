package lk.ijse.gdse.cielclothingstore.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    private String UserID;
    private String Name;
    private String ContactNo;
    private String Role;
    private String EmployeeID;
    private String Password;
    private String ConfirmPassword;
    private String UserName;
}
