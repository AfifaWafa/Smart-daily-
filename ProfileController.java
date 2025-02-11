package com.example.smart_daily;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProfileController {
    @FXML
    private TextField Username_TextField;
    @FXML
    private TextField PAssword_Textfield;
    @FXML
    private Button LOGIN;
    @FXML
    private Button fpass;
    @FXML
    private Button CREATE;

    private UserManager userManager = UserManager.getInstance();

    @FXML
    private void handleLoginButtonAction() {
        String username = Username_TextField.getText();
        String password = PAssword_Textfield.getText();

        if (userManager.login(username, password)) {
            try {
                // Open dashboard
                FXMLLoader loader = new FXMLLoader(getClass().getResource("expenseTracker_dashboard.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) LOGIN.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password!");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCreateAccountAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register_page.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) CREATE.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleForgotPasswordAction() {
        // Implement password recovery functionality
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password Recovery");
        alert.setHeaderText(null);
        alert.setContentText("Please contact support for password recovery.");
        alert.showAndWait();
    }
}