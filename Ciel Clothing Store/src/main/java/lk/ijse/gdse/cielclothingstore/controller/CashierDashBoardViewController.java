package lk.ijse.gdse.cielclothingstore.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class CashierDashBoardViewController extends Application {

    @FXML
    private JFXButton btnLoginPage;

    @FXML
    private AnchorPane content;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    void navigateToCustomerPage(ActionEvent event) { navigateTo("/view/CustomerView.fxml");}

    @FXML
    void navigateToPaymentDetailsPage(ActionEvent event) {navigateTo("/view/PaymentDetailsView.fxml");}
    @FXML
    void navigateToLoginPage(ActionEvent event) throws Exception {

        Window window = mainAnchorPane.getScene().getWindow();
        // window.hide();
        Stage stage = (Stage) window;
        stage.close();

        start(new Stage());

//        mainAnchorPane.getChildren().clear();
//        AnchorPane newPage = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
//        mainAnchorPane.getChildren().add(newPage);

    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("LoginPage");
        stage.show();
    }

    @FXML
    void navigateToOrderDetailsPage(ActionEvent event) {navigateTo("/view/OrderdetailsView.fxml");}

    @FXML
    void navigateToOrdersPage(ActionEvent event) {navigateTo("/view/OrderView.fxml");}

    @FXML
    void navigateToProductDetailsPage(ActionEvent event) {navigateTo("/view/ProductDetailsView.fxml");}

    @FXML
    void navigateToProductPage(ActionEvent event) {navigateTo("/view/ProductView.fxml");}

    private void navigateTo(String fxmlPath) {
        try{
            content.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            content.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load login page").show();
        }
    }
}





