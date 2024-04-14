package com.example.exerite_11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Exercise_Activity extends AppCompatActivity {
    private String userEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userEmail = getIntent().getStringExtra("USER_EMAIL");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
    }
    public void image_exersise(View view){
//        Intent intent = new Intent(this, NonVeg_Activity.class);
    }
}