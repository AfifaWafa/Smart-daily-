package com.example.smart_daily;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;

public class HomePageController {
    @FXML
    private Button get_Started1;
    @FXML
    private Button get_Started2;
    @FXML
    private Button get_Started3;
    @FXML
    private Button get_Started4;
    @FXML
    private Button logoutButton;
    @FXML
    private Button chatButton;

    @FXML
    private void initialize() {
        get_Started1.setOnAction(e -> openTaskTracker());
        get_Started2.setOnAction(e -> openExpenseTracker());
        get_Started3.setOnAction(e -> openTravelTracker());
        get_Started4.setOnAction(e -> openLearnDiscover());
    }

    @FXML
    private void handleLogout() {
        try {
            // Clear the user session
            UserSession.getInstance().clearSession();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();

            // Create new scene with correct dimensions
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Get the screen dimensions
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            // Set stage dimensions
            stage.setWidth(800);
            stage.setHeight(500);

            // Calculate the center position
            double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;

            // Set the window position
            stage.setX(centerX);
            stage.setY(centerY);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openTaskTracker() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("taskTracker_dashboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Task Tracker");

            // Create new scene with correct dimensions
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Get the screen dimensions
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            // Set stage dimensions
            stage.setWidth(400);
            stage.setHeight(700);

            // Calculate the center position
            double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;

            // Set the window position
            stage.setX(centerX);
            stage.setY(centerY);

            // Close the current window
            Stage currentStage = (Stage) get_Started1.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openExpenseTracker() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("expenseTracker_dashboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Expense Tracker");

            // Create new scene with correct dimensions
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Get the screen dimensions
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            // Set stage dimensions
            stage.setWidth(918);
            stage.setHeight(741);

            // Calculate the center position
            double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;

            // Set the window position
            stage.setX(centerX);
            stage.setY(centerY);

            // Close the current window
            Stage currentStage = (Stage) get_Started2.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openTravelTracker() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("travelEvent_dashboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Travel Event Tracker");

            // Create new scene with correct dimensions
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Get the screen dimensions
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            // Set stage dimensions
            stage.setWidth(400);
            stage.setHeight(700);

            // Calculate the center position
            double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;

            // Set the window position
            stage.setX(centerX);
            stage.setY(centerY);

            // Close the current window
            Stage currentStage = (Stage) get_Started3.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openLearnDiscover() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("learnDiscover_dashboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Learn & Discover");

            // Create new scene with correct dimensions
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Get the screen dimensions
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            // Set stage dimensions
            stage.setWidth(400);
            stage.setHeight(700);

            // Calculate the center position
            double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;

            // Set the window position
            stage.setX(centerX);
            stage.setY(centerY);

            // Close the current window
            Stage currentStage = (Stage) get_Started4.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOpenChat() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chat_window.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Smart Daily - Chat");

            // Create new scene with correct dimensions
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Get the screen dimensions
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            // Set stage dimensions
            stage.setWidth(400);
            stage.setHeight(500);

            // Calculate the center position
            double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;

            // Set the window position
            stage.setX(centerX);
            stage.setY(centerY);

            // Close the current window
            Stage currentStage = (Stage) chatButton.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}