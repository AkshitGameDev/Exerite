package com.example.exerite_11;

public class ExersiseModel {
    String Name = "";
    String Reps = "";

    public ExersiseModel(String name, String reps){
        this.Name = name;
        this.Reps = reps;
    }

    public String getName() {
        return Name;
    }

    public String getReps() {
        return Reps;
    }
}
