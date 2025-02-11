package com.example.smart_daily;

public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String country;
    private String dateOfBirth;
    private String postalCode;

    public User(String username, String password, String firstName, String lastName,
            String email, String phoneNumber, String country, String dateOfBirth, String postalCode) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.postalCode = postalCode;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return String.join(",", username, password, firstName, lastName,
                email, phoneNumber, country, dateOfBirth, postalCode);
    }
}