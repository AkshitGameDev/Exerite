package com.example.exerite_11;

import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signup_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Enable the back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find the "Already have an account?" TextView by its ID
        TextView textViewSignIn = findViewById(R.id.textViewSignIn);

        // Set OnClickListener to redirect to the login page when the TextView is clicked
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the LoginActivity
                startActivity(new Intent(Signup_activity.this, Login_activity.class));
            }
        });
    }

    // Handle back button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar back button click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // This will navigate back to the previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
