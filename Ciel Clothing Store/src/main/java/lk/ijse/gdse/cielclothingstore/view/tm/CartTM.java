package lk.ijse.gdse.cielclothingstore.view.tm;

import lombok.*;

import javafx.scene.control.Button;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartTM {
    private String ProductID;
    private String ProductName;
    private int cartQuantity;
    private double Price;
    private double Total;
    private Button removeButton;

}