package com.example.smart_daily;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import java.io.IOException;
import javafx.scene.Parent;

public class ChatController {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;
    @FXML
    private Button backButton;

    private ChatClient chatClient;
    private UserSession userSession;

    @FXML
    public void initialize() {
        userSession = UserSession.getInstance();
        String username = userSession.getUsername();

        // Initialize chat client
        chatClient = new ChatClient(username, chatArea);
        chatClient.connect();

        // Add welcome message
        chatArea.appendText("Welcome to the chat room, " + username + "!\n");
    }

    @FXML
    private void handleSendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            chatClient.sendMessage(message);
            messageField.clear();
        }
    }

    @FXML
    private void handleBack() {
        try {
            // Disconnect from chat
            if (chatClient != null) {
                chatClient.disconnect();
            }

            // Load dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Center the window
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setWidth(600);
            stage.setHeight(700);
            double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;
            stage.setX(centerX);
            stage.setY(centerY);

            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Could not load dashboard: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}