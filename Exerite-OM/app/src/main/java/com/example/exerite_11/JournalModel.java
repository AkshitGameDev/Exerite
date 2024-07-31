package com.example.exerite_11;

import java.io.Serializable;

public class JournalModel implements Serializable {
    private int id;
    private String title;
    private String description;
    private String email; // New field for email

    // Constructor
    public JournalModel(int id, String title, String description, String email) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.email = email;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email; // Getter for email
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmail(String email) {
        this.email = email; // Setter for email
    }

    @Override
    public String toString() {
        return "JournalModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' + // Include email in toString
                '}';
    }
}

