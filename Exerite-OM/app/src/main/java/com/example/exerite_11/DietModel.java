package com.example.exerite_11;

public class DietModel {
    private int dietId;
    private String email;
    private String category;
    private String dish;
    private String calories;

    // Constructor to initialize all fields
    public DietModel(int dietId, String email, String category, String dish, String calories) {
        this.dietId = dietId;
        this.email = email;
        this.category = category;
        this.dish = dish;
        this.calories = calories;
    }

    // Constructor without dietId, used when creating new entries before saving to DB
    public DietModel(String email, String category, String dish, String calories) {
        this.email = email;
        this.category = category;
        this.dish = dish;
        this.calories = calories;
    }

    // Getters
    public int getDietId() {
        return dietId;
    }

    public String getEmail() {
        return email;
    }

    public String getCategory() {
        return category;
    }

    public String getDish() {
        return dish;
    }

    public String getCalories() {
        return calories;
    }

    // Setters
    public void setDietId(int dietId) {
        this.dietId = dietId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
