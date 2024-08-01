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
    private EditText userEmail;
    private EditText password;
    private EditText username;
    private Button signup_btn;
    DBHelper dbobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Enable the back button in the action bar
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username =findViewById(R.id.editTextUsername);
        userEmail=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        signup_btn=findViewById(R.id.buttonSignUp);
        dbobj = new DBHelper(this);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });



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

    private void registerUser() {
        String enteredUsername = username.getText().toString().trim();
        String enteredEmail = userEmail.getText().toString().trim();
        String enteredPassword = password.getText().toString().trim();

        if (enteredUsername.isEmpty() || enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(enteredEmail)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if user already exists
        if (dbobj.checkUserEmail(enteredEmail)) {
            Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert user into the database
        boolean isInserted = dbobj.insertData(enteredUsername, enteredEmail, enteredPassword);
        if (isInserted) {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            // Redirect to login page
            startActivity(new Intent(Signup_activity.this, Login_activity.class));
            finish();
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
