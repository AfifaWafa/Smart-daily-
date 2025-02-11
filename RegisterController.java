package com.example.smart_daily;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField First_name_FIELD;
    @FXML
    private TextField Last_Name_FIELD;
    @FXML
    private TextField Email_Id_FIELD;
    @FXML
    private TextField phone_Field;
    @FXML
    private TextField Country_FIELD;
    @FXML
    private TextField Birth_FIELD;
    @FXML
    private TextField Postal_FIELD;
    @FXML
    private TextField ID_FIELD;
    @FXML
    private TextField Password_FIELD;
    @FXML
    private TextField Confirmpasword_FIELD;
    @FXML
    private Button Register_BUTTON;
    @FXML
    private Button backButton;

    private UserManager userManager = UserManager.getInstance();

    @FXML
    private void handleRegister() {
        if (validateFields()) {
            User newUser = new User(
                    ID_FIELD.getText(),
                    Password_FIELD.getText(),
                    First_name_FIELD.getText(),
                    Last_Name_FIELD.getText(),
                    Email_Id_FIELD.getText(),
                    phone_Field.getText(),
                    Country_FIELD.getText(),
                    Birth_FIELD.getText(),
                    Postal_FIELD.getText());

            if (userManager.registerUser(newUser)) {
                showAlert("Registration Successful", "You can now login with your credentials.",
                        Alert.AlertType.INFORMATION);
                navigateToLogin();
            } else {
                showAlert("Registration Failed", "Username already exists!", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean validateFields() {
        if (ID_FIELD.getText().isEmpty() || Password_FIELD.getText().isEmpty()) {
            showAlert("Validation Error", "Username and password are required!", Alert.AlertType.ERROR);
            return false;
        }

        if (!Password_FIELD.getText().equals(Confirmpasword_FIELD.getText())) {
            showAlert("Validation Error", "Passwords do not match!", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/smart_daily/login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ID_FIELD.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            // Load the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Get the current stage from the back button
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Create new scene and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load login page: " + e.getMessage());
            alert.showAndWait();
        }
    }
}