package com.example.exerite_11;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Diet_activity extends AppCompatActivity {
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.navigation_home) {
                        Intent intent = new Intent(Diet_activity.this, Home_Activity.class);
                        intent.putExtra("USER_EMAIL", userEmail);
                        startActivity(intent);
                        return true;
                    } else if (id == R.id.navigation_exercises) {
                        Toast.makeText(Diet_activity.this, "Redirecting to home activity", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (id == R.id.navigation_nutrition) {
                        // Handle nutrition navigation
                        return true;
                    } else if (id == R.id.navigation_progress) {
                        Toast.makeText(Diet_activity.this, "Redirecting to profile", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (id == R.id.navigation_profile) {
                        Toast.makeText(Diet_activity.this, "Redirecting to profile", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }
            };

    public void image_non_veg(View view) {
        // Handle click on non-veg image here
        Intent intent = new Intent(this, NonVeg_Activity.class);
        startActivity(intent);
    }
    public void image_veg(View view) {
        // Handle click on non-veg image here
        Intent intent = new Intent(this, NonVeg_Activity.class);
        startActivity(intent);
    }
    public void image_drinks(View view) {
        // Handle click on non-veg image here
        Intent intent = new Intent(this, NonVeg_Activity.class);
        startActivity(intent);
    }
}

