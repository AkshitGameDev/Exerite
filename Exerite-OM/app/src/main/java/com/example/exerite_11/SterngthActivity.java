package com.example.exerite_11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class SterngthActivity extends AppCompatActivity {

    ArrayList<ExersiseModel> ExersiseModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sterngth);
    }
    private Void setupExersiseModels(){
        String[] exeName = getResources().getStringArray(R.array.strength_exercise_names);
        String[] exeRep = getResources().getStringArray(R.array.strength_exercise_reps);

        for (int i = 0; i< exeName.length; i++){
            ExersiseModels.add(new ExersiseModel(exeName[i], exeRep[i]));
        }
        return null;
    }
}