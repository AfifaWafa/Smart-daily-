package com.example.smart_daily;

import java.time.LocalDate;
import java.io.Serializable;

public class Task implements Serializable {
    private String description;
    private String priority;
    private LocalDate dueDate;

    public Task(String description, String priority, LocalDate dueDate) {
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] - Due: %s", description, priority, dueDate);
    }
}