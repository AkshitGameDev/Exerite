package com.example.exerite_11;

public class CardioModel {
    String Name = "";
    String Time = "";

    public CardioModel(String name, String time){
        this.Name = name;
        this.Time = time;
    }

    public String getName() {
        return Name;
    }

    public String getTime() {
        return Time;
    }
}
