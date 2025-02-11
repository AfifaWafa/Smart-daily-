package com.example.smart_daily;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TravelEvent implements Serializable {
    private String destination;
    private String purpose;
    private LocalDate startDate;
    private LocalDate endDate;
    private double budget;
    private String transport;
    private String notes;

    public TravelEvent(String destination, String purpose, LocalDate startDate, LocalDate endDate,
            double budget, String transport, String notes) {
        this.destination = destination;
        this.purpose = purpose;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.transport = transport;
        this.notes = notes;
    }

    public String getDestination() {
        return destination;
    }

    public String getPurpose() {
        return purpose;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getBudget() {
        return budget;
    }

    public String getTransport() {
        return transport;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String result = String.format("%s\n%s - %s\nPurpose: %s\nBudget: BDT %.2f\nTransport: %s",
                destination,
                startDate.format(formatter),
                endDate.format(formatter),
                purpose,
                budget,
                transport);

        if (notes != null && !notes.trim().isEmpty()) {
            result += "\nNotes: " + notes;
        }

        return result;
    }
}