package com.example.smart_daily;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LearningManager {
    private static LearningManager instance;
    private static final String ENTRIES_FILE = "learning_entries.txt";
    private ObservableList<LearningEntry> allEntries;
    private ObservableList<LearningEntry> importantEntries;
    private UserSession userSession;

    private LearningManager() {
        allEntries = FXCollections.observableArrayList();
        importantEntries = FXCollections.observableArrayList();
        userSession = UserSession.getInstance();
        loadEntries();
    }

    public static LearningManager getInstance() {
        if (instance == null) {
            instance = new LearningManager();
        }
        return instance;
    }

    public void addEntry(LearningEntry entry) {
        allEntries.add(entry);

        if ("High".equals(entry.getImportance())) {
            importantEntries.add(entry);
        }

        saveEntries();
    }

    public ObservableList<LearningEntry> getAllEntries() {
        return allEntries;
    }

    public ObservableList<LearningEntry> getImportantEntries() {
        return importantEntries;
    }

    public ObservableList<LearningEntry> getEntriesByCategory(String category) {
        return FXCollections.observableArrayList(
                allEntries.stream()
                        .filter(entry -> entry.getCategory().equals(category))
                        .collect(Collectors.toList()));
    }

    private void saveEntries() {
        String userDir = userSession.getUserDataPath();
        new File(userDir).mkdirs(); // Create user directory if it doesn't exist

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userDir + ENTRIES_FILE))) {
            for (LearningEntry entry : allEntries) {
                // Format: topic|category|content|date|importance
                writer.write(String.format("%s|%s|%s|%s|%s%n",
                        entry.getTopic(),
                        entry.getCategory(),
                        entry.getContent().replace("\n", "\\n"),
                        entry.getDate().toString(),
                        entry.getImportance()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEntries() {
        String userDir = userSession.getUserDataPath();
        File entriesFile = new File(userDir + ENTRIES_FILE);

        allEntries.clear();
        importantEntries.clear();

        if (entriesFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(entriesFile))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 5) {
                        String topic = parts[0];
                        String category = parts[1];
                        String content = parts[2].replace("\\n", "\n");
                        LocalDate date = LocalDate.parse(parts[3]);
                        String importance = parts[4];

                        LearningEntry entry = new LearningEntry(topic, category, content, date, importance);
                        allEntries.add(entry);

                        if ("High".equals(importance)) {
                            importantEntries.add(entry);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}