package com.example.smart_daily;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private String username;
    private TextArea chatArea;
    private boolean isConnected = false;
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ChatClient(String username, TextArea chatArea) {
        this.username = username;
        this.chatArea = chatArea;
    }

    public void connect() {
        if (isConnected)
            return;

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            isConnected = true;

            // Send username as first message
            writer.println(username);

            // Start message receiving thread
            new Thread(this::receiveMessages).start();
        } catch (IOException e) {
            appendToChatArea("Could not connect to server: " + e.getMessage());
        }
    }

    public void disconnect() {
        isConnected = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (isConnected && writer != null) {
            // Show own message with timestamp
            String timestamp = LocalDateTime.now().format(timeFormatter);
            appendToChatArea(String.format("[%s] You: %s", timestamp, message));

            // Send to server
            writer.println(message);
        }
    }

    private void receiveMessages() {
        try {
            String message;
            while (isConnected && (message = reader.readLine()) != null) {
                final String timestamp = LocalDateTime.now().format(timeFormatter);
                final String finalMessage = String.format("[%s] %s", timestamp, message);
                Platform.runLater(() -> appendToChatArea(finalMessage));
            }
        } catch (IOException e) {
            if (isConnected) {
                Platform.runLater(() -> appendToChatArea("Lost connection to server: " + e.getMessage()));
            }
        }
    }

    private void appendToChatArea(String message) {
        chatArea.appendText(message + "\n");
        // Auto-scroll to bottom
        chatArea.setScrollTop(Double.MAX_VALUE);
    }

    public boolean isConnected() {
        return isConnected;
    }
}