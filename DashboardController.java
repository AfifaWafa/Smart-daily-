package com.example.smart_daily;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class DashboardController {
    @FXML
    private Label balance_field;
    @FXML
    private TableView<Expense> transactionTable;
    @FXML
    private TableColumn<Expense, String> purposeColumn;
    @FXML
    private TableColumn<Expense, String> categoryColumn;
    @FXML
    private TableColumn<Expense, Double> sumColumn;
    @FXML
    private TableColumn<Expense, LocalDate> dateColumn;
    @FXML
    private TextField purposeField;
    @FXML
    private TextField sumField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private Label shoppingLabel;
    @FXML
    private Label foodLabel;
    @FXML
    private Label billsLabel;
    @FXML
    private Label tuitionLabel;
    @FXML
    private Label otherLabel;
    @FXML
    private Button logoutButton;
    @FXML
    private Button backButton;

    private ExpenseManager expenseManager;
    private UserManager userManager;

    @FXML
    public void initialize() {
        expenseManager = ExpenseManager.getInstance();
        userManager = UserManager.getInstance();

        // Initialize table columns
        purposeColumn.setCellValueFactory(new PropertyValueFactory<>("purpose"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Initialize category combo box
        categoryComboBox.setItems(FXCollections.observableArrayList(
                "Shopping", "Food & Drinks", "Bills & Utilities", "Tuition Fees", "Other"));

        // Set default values for labels
        balance_field.setText("BDT 0.00");
        shoppingLabel.setText("BDT 0.00");
        foodLabel.setText("BDT 0.00");
        billsLabel.setText("BDT 0.00");
        tuitionLabel.setText("BDT 0.00");
        otherLabel.setText("BDT 0.00");

        // Set default date to today
        datePicker.setValue(LocalDate.now());

        // Load user's expenses
        loadUserExpenses();
        updateCategoryLabels();
    }

    private void loadUserExpenses() {
        try {
            String userId = userManager.getCurrentUser().getUsername();
            ObservableList<Expense> expenses = FXCollections.observableArrayList(
                    expenseManager.getUserExpenses(userId));
            transactionTable.setItems(expenses);
            updateBalance();
        } catch (Exception e) {
            showAlert("Error", "Failed to load expenses: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updateBalance() {
        try {
            String userId = userManager.getCurrentUser().getUsername();
            double total = expenseManager.getTotalBalance(userId);
            balance_field.setText(String.format("BDT %.2f", total));
        } catch (Exception e) {
            showAlert("Error", "Failed to update balance: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updateCategoryLabels() {
        try {
            String userId = userManager.getCurrentUser().getUsername();
            Map<String, Double> categoryTotals = expenseManager.getCategoryTotals(userId);

            shoppingLabel.setText(String.format("BDT %.2f", categoryTotals.getOrDefault("Shopping", 0.0)));
            foodLabel.setText(String.format("BDT %.2f", categoryTotals.getOrDefault("Food & Drinks", 0.0)));
            billsLabel.setText(String.format("BDT %.2f", categoryTotals.getOrDefault("Bills & Utilities", 0.0)));
            tuitionLabel.setText(String.format("BDT %.2f", categoryTotals.getOrDefault("Tuition Fees", 0.0)));
            otherLabel.setText(String.format("BDT %.2f", categoryTotals.getOrDefault("Other", 0.0)));
        } catch (Exception e) {
            showAlert("Error", "Failed to update category totals: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSubmit() {
        try {
            String purpose = purposeField.getText();
            String category = categoryComboBox.getValue();
            String amountText = sumField.getText();
            LocalDate date = datePicker.getValue();

            if (purpose.isEmpty() || category == null || amountText.isEmpty() || date == null) {
                showAlert("Error", "Please fill all fields", Alert.AlertType.ERROR);
                return;
            }

            double amount = Double.parseDouble(amountText);
            String userId = userManager.getCurrentUser().getUsername();

            Expense expense = new Expense(purpose, category, amount, date, userId);
            expenseManager.addExpense(expense);

            // Clear fields and refresh
            clearFields();
            loadUserExpenses();
            updateCategoryLabels();

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount format", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "An error occurred while adding the expense", Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        purposeField.clear();
        sumField.clear();
        datePicker.setValue(LocalDate.now());
        categoryComboBox.setValue(null);
    }

    @FXML
    private void handleLogout() {
        try {
            // Load the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Get the current stage from the logout button
            Stage stage = (Stage) logoutButton.getScene().getWindow();

            // Create new scene and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Could not load login page: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleBack() {
        try {
            // Load the main dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Scene scene = new Scene(loader.load(), 600, 700);
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Get the screen dimensions
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            // Calculate the center position
            double centerX = (screenBounds.getWidth() - scene.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - scene.getHeight()) / 2;

            // Set the window position
            stage.setX(centerX);
            stage.setY(centerY);

            stage.setScene(scene);
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