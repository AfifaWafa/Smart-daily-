package com.example.smart_daily;

public class UserSession {
    private static UserSession instance;
    private String username;

    private UserSession() {
        // Private constructor for singleton
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void clearSession() {
        username = null;
    }

    public String getUserDataPath() {
        return "user_data/" + username + "/";
    }
}