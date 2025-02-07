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

public class ManagerViewController extends Application {

    @FXML
    private JFXButton btnLoginPage;

    @FXML
    private AnchorPane content;

    @FXML
    private AnchorPane mainAnchorPane;


    @FXML
    void navigateToPaymentDetailsPage(ActionEvent event) {navigateTo("/view/PaymentDetailsView.fxml");}
    @FXML
    void navigateToCustomerPage(ActionEvent event) {
        navigateTo("/view/CustomerView.fxml");
    }

    @FXML
    void navigateToEmployeePage(ActionEvent event) {
        navigateTo("/view/EmployeeView.fxml");
    }

    @FXML
    void navigateToLoginPage(ActionEvent event) throws Exception {
        Window window = mainAnchorPane.getScene().getWindow();
        // window.hide();
        Stage stage = (Stage) window;
        stage.close();

        start(new Stage());
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
    void navigateToOrderDetailsPage(ActionEvent event) {
        navigateTo("/view/OrderDetailsView.fxml");
    }

    @FXML
    void navigateToOrdersPage(ActionEvent event) {
        navigateTo("/view/OrderView.fxml");
    }

    @FXML
    void navigateToProductDetailsPage(ActionEvent event) {
        navigateTo("/view/ProductDetailsView.fxml");
    }

    @FXML
    void navigateToProductPage(ActionEvent event) {
        navigateTo("/view/ProductView.fxml");
    }

    @FXML
    void navigateToUserPage(ActionEvent event) {
        navigateTo("/view/UserView.fxml");
    }

    @FXML
    void navigateToSupplierPage(ActionEvent event) {
        navigateTo("/view/SupplierView.fxml");
    }

    private void navigateTo(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));

            content.getChildren().clear();
            content.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the page: " + e.getMessage()).show();
        }
    }
}

