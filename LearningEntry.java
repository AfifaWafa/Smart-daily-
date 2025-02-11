package com.example.smart_daily;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LearningEntry implements Serializable {
    private String topic;
    private String category;
    private String content;
    private LocalDate date;
    private String importance;

    public LearningEntry(String topic, String category, String content, LocalDate date, String importance) {
        this.topic = topic;
        this.category = category;
        this.content = content;
        this.date = date;
        this.importance = importance;
    }

    public String getTopic() {
        return topic;
    }

    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getImportance() {
        return importance;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        StringBuilder sb = new StringBuilder();

        sb.append(topic).append(" [").append(importance).append("]\n");
        sb.append("Category: ").append(category).append("\n");
        sb.append("Date: ").append(date.format(formatter)).append("\n");
        sb.append("Content:\n").append(content);

        return sb.toString();
    }
}