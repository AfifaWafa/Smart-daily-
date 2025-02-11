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

public class LearnDiscoverController {
    @FXML
    private TextField topicField;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TextArea contentArea;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> importanceComboBox;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private ListView<LearningEntry> allEntriesList;
    @FXML
    private ListView<LearningEntry> categorizedList;
    @FXML
    private ListView<LearningEntry> importantList;

    private LearningManager learningManager;
    private ObservableList<String> categories = FXCollections.observableArrayList(
            "Technology", "Science", "Arts", "Literature", "History",
            "Mathematics", "Language", "Personal Development", "Other");

    @FXML
    public void initialize() {
        learningManager = LearningManager.getInstance();

        // Initialize category options
        categoryComboBox.setItems(categories);

        // Initialize importance options
        importanceComboBox.setItems(FXCollections.observableArrayList(
                "High", "Medium", "Low"));

        // Set default date to today
        datePicker.setValue(LocalDate.now());

        // Bind lists to LearningManager's ObservableLists
        allEntriesList.setItems(learningManager.getAllEntries());
        importantList.setItems(learningManager.getImportantEntries());

        // Set cell factories for all ListViews
        allEntriesList.setCellFactory(listView -> new ListCell<LearningEntry>() {
            @Override
            protected void updateItem(LearningEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText(entry.toString());
                }
            }
        });

        categorizedList.setCellFactory(listView -> new ListCell<LearningEntry>() {
            @Override
            protected void updateItem(LearningEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText(entry.toString());
                }
            }
        });

        importantList.setCellFactory(listView -> new ListCell<LearningEntry>() {
            @Override
            protected void updateItem(LearningEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText(entry.toString());
                }
            }
        });

        // Add listener for category selection to update categorized list
        categoryComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                categorizedList.setItems(learningManager.getEntriesByCategory(newVal));
            }
        });
    }

    @FXML
    private void handleAddEntry() {
        if (!validateInputs()) {
            return;
        }

        String topic = topicField.getText();
        String category = categoryComboBox.getValue();
        String content = contentArea.getText();
        LocalDate date = datePicker.getValue();
        String importance = importanceComboBox.getValue();

        // Create and add new entry
        LearningEntry newEntry = new LearningEntry(topic, category, content, date, importance);
        learningManager.addEntry(newEntry);

        // Clear input fields
        clearFields();
        showAlert("Success", "Learning entry added successfully!", Alert.AlertType.INFORMATION);
    }

    private boolean validateInputs() {
        if (topicField.getText().trim().isEmpty()) {
            showAlert("Error", "Please enter a topic", Alert.AlertType.ERROR);
            return false;
        }

        if (categoryComboBox.getValue() == null) {
            showAlert("Error", "Please select a category", Alert.AlertType.ERROR);
            return false;
        }

        if (contentArea.getText().trim().isEmpty()) {
            showAlert("Error", "Please enter some content", Alert.AlertType.ERROR);
            return false;
        }

        if (datePicker.getValue() == null) {
            showAlert("Error", "Please select a date", Alert.AlertType.ERROR);
            return false;
        }

        if (importanceComboBox.getValue() == null) {
            showAlert("Error", "Please select importance level", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void clearFields() {
        topicField.clear();
        categoryComboBox.setValue(null);
        contentArea.clear();
        datePicker.setValue(LocalDate.now());
        importanceComboBox.setValue(null);
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