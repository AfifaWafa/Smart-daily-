package com.example.smart_daily;

import java.time.LocalDate;

public class Expense {
    private String purpose;
    private String category;
    private double amount;
    private LocalDate date;
    private String userId;

    public Expense(String purpose, String category, double amount, LocalDate date, String userId) {
        this.purpose = purpose;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.userId = userId;
    }

    // Getters and setters
    public String getPurpose() {
        return purpose;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return String.join(",", purpose, category, String.valueOf(amount), date.toString(), userId);
    }
}