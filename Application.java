package com.example.smart_daily;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    private static boolean isFirstInstance = true;

    @Override
    public void start(Stage stage) throws Exception {
        ChatServer chatServer = ChatServer.getInstance();

        // Try to start the server if it's not already running
        if (!chatServer.isServerRunning()) {
            chatServer.start();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Smart Daily - Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        // Only stop the server if we started it
        if (isFirstInstance) {
            ChatServer.getInstance().stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}