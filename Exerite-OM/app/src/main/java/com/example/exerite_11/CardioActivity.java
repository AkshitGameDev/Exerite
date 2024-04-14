package com.example.exerite_11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class CardioActivity extends AppCompatActivity {

    ArrayList<CardioModel> cardioModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio);
    }
    private Void setupCardioModels(){
        String[] carName = getResources().getStringArray(R.array.cardio_exercise_names);
        String[] carTime = getResources().getStringArray(R.array.cardio_exercise_times);

        for (int i = 0; i< carName.length; i++){
            cardioModels.add(new CardioModel(carName[i], carTime[i]));
        }
        return null;
    }
}