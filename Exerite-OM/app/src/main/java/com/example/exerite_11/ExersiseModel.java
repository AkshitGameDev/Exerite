package com.example.exerite_11;

public class ExersiseModel {
    String workout_id;
    String email;
    String workout_name;
    String workout_description;
    String workout_category;


    public String getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(String workout_id) {
        this.workout_id = workout_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkout_name() {
        return workout_name;
    }

    public void setWorkout_name(String workout_name) {
        this.workout_name = workout_name;
    }

    public String getWorkout_description() {
        return workout_description;
    }

    public void setWorkout_description(String workout_description) {
        this.workout_description = workout_description;
    }

    public String getWorkout_category() {
        return workout_category;
    }

    public void setWorkout_category(String workout_category) {
        this.workout_category = workout_category;
    }


    public ExersiseModel(String email, String workout_name, String workout_description, String workout_category) {
        this.email = email;
        this.workout_name = workout_name;
        this.workout_description = workout_description;
        this.workout_category = workout_category;
    }


    public ExersiseModel(String workout_id, String email, String workout_name, String workout_description, String workout_category) {
        this.workout_id = workout_id;
        this.email = email;
        this.workout_name = workout_name;
        this.workout_description = workout_description;
        this.workout_category = workout_category;
    }







}
