package com.example.smart_daily;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseManager {
    private static final String EXPENSE_FILE = "expenses.txt";
    private Map<String, List<Expense>> userExpenses;
    private static ExpenseManager instance;

    private ExpenseManager() {
        userExpenses = new HashMap<>();
        loadExpenses();
    }

    public static ExpenseManager getInstance() {
        if (instance == null) {
            instance = new ExpenseManager();
        }
        return instance;
    }

    private void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(EXPENSE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Expense expense = new Expense(
                            parts[0], // purpose
                            parts[1], // category
                            Double.parseDouble(parts[2]), // amount
                            LocalDate.parse(parts[3]), // date
                            parts[4] // userId
                    );
                    addExpenseToMap(expense);
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
    }

    private void saveExpenses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EXPENSE_FILE))) {
            for (List<Expense> expenses : userExpenses.values()) {
                for (Expense expense : expenses) {
                    writer.println(expense.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addExpenseToMap(Expense expense) {
        userExpenses.computeIfAbsent(expense.getUserId(), k -> new ArrayList<>()).add(expense);
    }

    public void addExpense(Expense expense) {
        addExpenseToMap(expense);
        saveExpenses();
    }

    public List<Expense> getUserExpenses(String userId) {
        return userExpenses.getOrDefault(userId, new ArrayList<>());
    }

    public Map<String, Double> getCategoryTotals(String userId) {
        Map<String, Double> totals = new HashMap<>();
        List<Expense> expenses = getUserExpenses(userId);

        for (Expense expense : expenses) {
            totals.merge(expense.getCategory(), expense.getAmount(), Double::sum);
        }
        return totals;
    }

    public double getTotalBalance(String userId) {
        return getUserExpenses(userId).stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
}