package com.example.smart_daily;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskManager {
    private static TaskManager instance;
    private static final String TASKS_FILE = "tasks.txt";
    private ObservableList<Task> allTasks;
    private ObservableList<Task> todayTasks;
    private ObservableList<Task> upcomingTasks;
    private UserSession userSession;

    private TaskManager() {
        allTasks = FXCollections.observableArrayList();
        todayTasks = FXCollections.observableArrayList();
        upcomingTasks = FXCollections.observableArrayList();
        userSession = UserSession.getInstance();
        loadTasks();
    }

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public void addTask(Task task) {
        allTasks.add(task);

        LocalDate today = LocalDate.now();
        if (task.getDueDate().equals(today)) {
            todayTasks.add(task);
        } else if (task.getDueDate().isAfter(today)) {
            upcomingTasks.add(task);
        }

        saveTasks();
    }

    public ObservableList<Task> getAllTasks() {
        return allTasks;
    }

    public ObservableList<Task> getTodayTasks() {
        return todayTasks;
    }

    public ObservableList<Task> getUpcomingTasks() {
        return upcomingTasks;
    }

    private void saveTasks() {
        String userDir = userSession.getUserDataPath();
        new File(userDir).mkdirs(); // Create user directory if it doesn't exist

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userDir + TASKS_FILE))) {
            for (Task task : allTasks) {
                // Format: description|priority|dueDate
                writer.write(String.format("%s|%s|%s%n",
                        task.getDescription(),
                        task.getPriority(),
                        task.getDueDate().toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        String userDir = userSession.getUserDataPath();
        File tasksFile = new File(userDir + TASKS_FILE);

        allTasks.clear();
        todayTasks.clear();
        upcomingTasks.clear();

        if (tasksFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(tasksFile))) {
                String line;
                LocalDate today = LocalDate.now();

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 3) {
                        String description = parts[0];
                        String priority = parts[1];
                        LocalDate dueDate = LocalDate.parse(parts[2]);

                        Task task = new Task(description, priority, dueDate);
                        allTasks.add(task);

                        if (dueDate.equals(today)) {
                            todayTasks.add(task);
                        } else if (dueDate.isAfter(today)) {
                            upcomingTasks.add(task);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshData() {
        loadTasks();
    }
}