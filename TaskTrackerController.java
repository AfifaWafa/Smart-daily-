package com.example.smart_daily;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.time.LocalDate;
import javafx.scene.Parent;

public class TaskTrackerController {
    @FXML
    private TextField taskField;
    @FXML
    private ComboBox<String> priorityComboBox;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private ListView<Task> allTasksList;
    @FXML
    private ListView<Task> todayTasksList;
    @FXML
    private ListView<Task> upcomingTasksList;

    private TaskManager taskManager;

    @FXML
    public void initialize() {
        taskManager = TaskManager.getInstance();

        // Initialize priority options
        priorityComboBox.setItems(FXCollections.observableArrayList(
                "High", "Medium", "Low"));

        // Set default date to today
        dueDatePicker.setValue(LocalDate.now());

        // Bind lists to TaskManager's ObservableLists
        allTasksList.setItems(taskManager.getAllTasks());
        todayTasksList.setItems(taskManager.getTodayTasks());
        upcomingTasksList.setItems(taskManager.getUpcomingTasks());

        // Set cell factories for all ListViews
        allTasksList.setCellFactory(listView -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                } else {
                    setText(task.toString());
                }
            }
        });

        todayTasksList.setCellFactory(listView -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                } else {
                    setText(task.toString());
                }
            }
        });

        upcomingTasksList.setCellFactory(listView -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                } else {
                    setText(task.toString());
                }
            }
        });

        // Add date validation
        dueDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date == null || date.isBefore(LocalDate.now()));
            }
        });
    }

    @FXML
    private void handleAddTask() {
        String taskDescription = taskField.getText();
        String priority = priorityComboBox.getValue();
        LocalDate dueDate = dueDatePicker.getValue();

        if (taskDescription == null || taskDescription.trim().isEmpty()) {
            showAlert("Error", "Please enter a task description", Alert.AlertType.ERROR);
            return;
        }

        if (priority == null) {
            showAlert("Error", "Please select a priority", Alert.AlertType.ERROR);
            return;
        }

        if (dueDate == null) {
            showAlert("Error", "Please select a due date", Alert.AlertType.ERROR);
            return;
        }

        // Create and add new task
        Task newTask = new Task(taskDescription, priority, dueDate);
        taskManager.addTask(newTask);

        // Clear input fields
        taskField.clear();
        priorityComboBox.setValue(null);
        dueDatePicker.setValue(LocalDate.now());

        showAlert("Success", "Task added successfully!", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Create new scene with correct dimensions
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Get the screen dimensions
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            // Set stage dimensions
            stage.setWidth(600);
            stage.setHeight(700);

            // Calculate the center position
            double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;

            // Set the window position
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