package lk.ijse.gdse.cielclothingstore.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDto {
    private String CustomerID;
    private String UserID;
    private String Name;
    private String Priority;
    private String ContactNo;
    private String Email;
}