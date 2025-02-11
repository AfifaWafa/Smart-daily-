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
import java.time.format.DateTimeFormatter;
import javafx.scene.Parent;

public class TravelEventController {
    @FXML
    private TextField destinationField;
    @FXML
    private TextField purposeField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField budgetField;
    @FXML
    private ComboBox<String> transportComboBox;
    @FXML
    private TextArea notesArea;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private ListView<TravelEvent> upcomingEventsList;
    @FXML
    private ListView<TravelEvent> pastEventsList;
    @FXML
    private ListView<TravelEvent> allEventsList;

    private TravelEventManager eventManager;

    @FXML
    public void initialize() {
        eventManager = TravelEventManager.getInstance();

        // Initialize transport options
        transportComboBox.setItems(FXCollections.observableArrayList(
                "Air", "Bus", "Train", "Car", "Ship", "Other"));

        // Set default dates
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now().plusDays(1));

        // Bind lists to EventManager's ObservableLists
        upcomingEventsList.setItems(eventManager.getUpcomingEvents());
        pastEventsList.setItems(eventManager.getPastEvents());
        allEventsList.setItems(eventManager.getAllEvents());

        // Set cell factories for all ListViews
        upcomingEventsList.setCellFactory(listView -> new ListCell<TravelEvent>() {
            @Override
            protected void updateItem(TravelEvent event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null) {
                    setText(null);
                } else {
                    setText(event.toString());
                }
            }
        });

        pastEventsList.setCellFactory(listView -> new ListCell<TravelEvent>() {
            @Override
            protected void updateItem(TravelEvent event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null) {
                    setText(null);
                } else {
                    setText(event.toString());
                }
            }
        });

        allEventsList.setCellFactory(listView -> new ListCell<TravelEvent>() {
            @Override
            protected void updateItem(TravelEvent event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null) {
                    setText(null);
                } else {
                    setText(event.toString());
                }
            }
        });

        // Add date validation
        endDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate startDate = startDatePicker.getValue();
                setDisable(empty || date == null || date.compareTo(startDate) < 0);
            }
        });
    }

    @FXML
    private void handleAddEvent() {
        // Validate input fields
        if (!validateInputs()) {
            return;
        }

        try {
            String destination = destinationField.getText();
            String purpose = purposeField.getText();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            double budget = Double.parseDouble(budgetField.getText());
            String transport = transportComboBox.getValue();
            String notes = notesArea.getText().trim();

            // Create and add new event
            TravelEvent newEvent = new TravelEvent(destination, purpose, startDate, endDate, budget, transport, notes);
            eventManager.addEvent(newEvent);

            // Clear input fields
            clearFields();
            showAlert("Success", "Travel event added successfully!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid budget amount", Alert.AlertType.ERROR);
        }
    }

    private boolean validateInputs() {
        if (destinationField.getText().trim().isEmpty()) {
            showAlert("Error", "Please enter a destination", Alert.AlertType.ERROR);
            return false;
        }

        if (purposeField.getText().trim().isEmpty()) {
            showAlert("Error", "Please enter a purpose", Alert.AlertType.ERROR);
            return false;
        }

        if (startDatePicker.getValue() == null) {
            showAlert("Error", "Please select a start date", Alert.AlertType.ERROR);
            return false;
        }

        if (endDatePicker.getValue() == null) {
            showAlert("Error", "Please select an end date", Alert.AlertType.ERROR);
            return false;
        }

        if (budgetField.getText().trim().isEmpty()) {
            showAlert("Error", "Please enter a budget", Alert.AlertType.ERROR);
            return false;
        }

        if (transportComboBox.getValue() == null) {
            showAlert("Error", "Please select a mode of transport", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void clearFields() {
        destinationField.clear();
        purposeField.clear();
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now().plusDays(1));
        budgetField.clear();
        transportComboBox.setValue(null);
        notesArea.clear();
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