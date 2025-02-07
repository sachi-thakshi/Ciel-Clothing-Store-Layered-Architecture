package lk.ijse.gdse.cielclothingstore.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Employee implements Serializable {
    private String EmployeeID;
    private String Name;
    private double Salary;
    private String Role;
    private String ContactNo;
}
