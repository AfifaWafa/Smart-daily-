package com.example.smart_daily;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TravelEventManager {
    private static TravelEventManager instance;
    private static final String EVENTS_FILE = "travel_events.txt";
    private ObservableList<TravelEvent> allEvents;
    private ObservableList<TravelEvent> upcomingEvents;
    private ObservableList<TravelEvent> pastEvents;
    private UserSession userSession;

    private TravelEventManager() {
        allEvents = FXCollections.observableArrayList();
        upcomingEvents = FXCollections.observableArrayList();
        pastEvents = FXCollections.observableArrayList();
        userSession = UserSession.getInstance();
        loadEvents();
    }

    public static TravelEventManager getInstance() {
        if (instance == null) {
            instance = new TravelEventManager();
        }
        return instance;
    }

    public void addEvent(TravelEvent event) {
        allEvents.add(event);

        LocalDate today = LocalDate.now();
        if (event.getStartDate().isAfter(today)) {
            upcomingEvents.add(event);
        } else if (event.getEndDate().isBefore(today)) {
            pastEvents.add(event);
        }

        saveEvents();
    }

    public ObservableList<TravelEvent> getAllEvents() {
        return allEvents;
    }

    public ObservableList<TravelEvent> getUpcomingEvents() {
        return upcomingEvents;
    }

    public ObservableList<TravelEvent> getPastEvents() {
        return pastEvents;
    }

    private void saveEvents() {
        String userDir = userSession.getUserDataPath();
        new File(userDir).mkdirs(); // Create user directory if it doesn't exist

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userDir + EVENTS_FILE))) {
            for (TravelEvent event : allEvents) {
                // Format: destination|purpose|startDate|endDate|budget|transport|notes
                writer.write(String.format("%s|%s|%s|%s|%.2f|%s|%s%n",
                        event.getDestination(),
                        event.getPurpose(),
                        event.getStartDate().toString(),
                        event.getEndDate().toString(),
                        event.getBudget(),
                        event.getTransport(),
                        event.getNotes() != null ? event.getNotes() : ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEvents() {
        String userDir = userSession.getUserDataPath();
        File eventsFile = new File(userDir + EVENTS_FILE);

        allEvents.clear();
        upcomingEvents.clear();
        pastEvents.clear();

        if (eventsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(eventsFile))) {
                String line;
                LocalDate today = LocalDate.now();

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 7) {
                        String destination = parts[0];
                        String purpose = parts[1];
                        LocalDate startDate = LocalDate.parse(parts[2]);
                        LocalDate endDate = LocalDate.parse(parts[3]);
                        double budget = Double.parseDouble(parts[4]);
                        String transport = parts[5];
                        String notes = parts[6];

                        TravelEvent event = new TravelEvent(destination, purpose, startDate, endDate,
                                budget, transport, notes);
                        allEvents.add(event);

                        if (startDate.isAfter(today)) {
                            upcomingEvents.add(event);
                        } else if (endDate.isBefore(today)) {
                            pastEvents.add(event);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshData() {
        loadEvents();
    }
}