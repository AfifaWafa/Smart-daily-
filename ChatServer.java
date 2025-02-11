package com.example.smart_daily;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static ChatServer instance;
    private ServerSocket serverSocket;
    private final ConcurrentHashMap<String, PrintWriter> clients = new ConcurrentHashMap<>();
    private boolean isRunning = false;

    private ChatServer() {
        // Private constructor for singleton
    }

    public static ChatServer getInstance() {
        if (instance == null) {
            instance = new ChatServer();
        }
        return instance;
    }

    public boolean isServerRunning() {
        try (Socket socket = new Socket("localhost", PORT)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void start() {
        if (isRunning)
            return;

        // Check if server is already running
        if (isServerRunning()) {
            System.out.println("Chat server is already running on port " + PORT);
            isRunning = true;
            return;
        }

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                isRunning = true;
                System.out.println("Chat Server started on port " + PORT);

                while (isRunning) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(new ClientHandler(clientSocket)).start();
                }
            } catch (BindException e) {
                System.out.println("Chat server is already running on port " + PORT);
                isRunning = true;
            } catch (IOException e) {
                if (isRunning) {
                    System.out.println("Server error: " + e.getMessage());
                }
            }
        }).start();
    }

    public void stop() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            clients.clear();
        } catch (IOException e) {
            System.out.println("Error stopping server: " + e.getMessage());
        }
    }

    private void broadcast(String message, String sender) {
        for (Map.Entry<String, PrintWriter> client : clients.entrySet()) {
            if (!client.getKey().equals(sender)) {
                client.getValue().println(message);
                client.getValue().flush();
            }
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                // First message from client should be their username
                username = reader.readLine();
                clients.put(username, writer);
                broadcast(username + " has joined the chat!", username);

                String message;
                while ((message = reader.readLine()) != null) {
                    broadcast(username + ": " + message, username);
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    clients.remove(username);
                    if (username != null) {
                        broadcast(username + " has left the chat!", username);
                    }
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }
    }
}
