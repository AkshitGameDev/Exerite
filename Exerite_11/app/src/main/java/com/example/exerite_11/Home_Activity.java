package com.example.exerite_11;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class Home_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Retrieve the login status from Intent extras
        boolean isLogged = getIntent().getBooleanExtra("isLogged", false);
        // Check if the user is logged in
        if (isLogged) {
            Toast.makeText(this, "Welcome to HomeActivity!", Toast.LENGTH_SHORT).show();
        } else {
            // If the user is not logged in, navigate back to LoginActivity or handle accordingly
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            // Example: You can start LoginActivity or finish this activity and handle logout scenario
        }
    }


}
