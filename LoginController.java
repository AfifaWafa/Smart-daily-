package com.example.smart_daily;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button forgotPasswordButton;

    private UserManager userManager = UserManager.getInstance();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password", Alert.AlertType.ERROR);
            return;
        }

        if (userManager.login(username, password)) {
            try {
                // Set the user session
                UserSession.getInstance().setUsername(username);

                // Load and center the dashboard
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) usernameField.getScene().getWindow();

                // Create new scene with correct dimensions
                Scene scene = new Scene(root);
                stage.setScene(scene);

                // Get the screen dimensions
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

                // Set stage dimensions
                stage.setWidth(600);
                stage.setHeight(700);

                // Calculate the center position
                double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
                double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;

                // Set the window position
                stage.setX(centerX);
                stage.setY(centerY);

                stage.show();
            } catch (IOException e) {
                showAlert("Error", "Could not load dashboard: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Login Failed", "Invalid username or password", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/smart_daily/register_page.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setTitle("Smart Daily - Register");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load registration page: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleForgotPassword() {
        showAlert("Password Recovery", "Please contact support for password recovery", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}