package com.example.exerite_11;

import android.widget.TextView;

public class DietModel {
    String Dish;
    String  Calories;


    public DietModel(String dish, String calories) {
        Dish = dish;
        Calories = calories;
    }

    public String getDish() {
        return Dish;
    }

    public String getCalories() {
        return Calories;
    }
}
