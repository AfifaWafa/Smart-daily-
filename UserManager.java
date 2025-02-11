package com.example.smart_daily;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String USER_FILE = "users.txt";
    private Map<String, User> users;
    private static UserManager instance;
    private User currentUser;

    private UserManager() {
        users = new HashMap<>();
        loadUsers();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    User user = new User(parts[0], parts[1], parts[2], parts[3],
                            parts[4], parts[5], parts[6], parts[7], parts[8]);
                    users.put(parts[0], user);
                }
            }
        } catch (IOException e) {
            // File might not exist yet, which is fine for first run
        }
    }

    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE))) {
            for (User user : users.values()) {
                writer.println(user.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        saveUsers();
        return true;
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}