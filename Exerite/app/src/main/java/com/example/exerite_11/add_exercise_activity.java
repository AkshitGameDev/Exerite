package com.example.exerite_11;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class add_exercise_activity extends AppCompatActivity {
    private String userEmail; // Declare userEmail at the class level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        // Retrieve user email from intent
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        // Check if user email is not null before displaying
        if (userEmail != null) {
            Toast.makeText(this, "Welcome! " + userEmail, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User email is null", Toast.LENGTH_SHORT).show();
        }
    }
}
