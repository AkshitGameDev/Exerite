package com.example.exerite_11;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class addDishesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dishes);
        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.navigation_home) {
                        Intent intent = new Intent(addDishesActivity.this, Home_Activity.class);
                        startActivity(intent);
                        return true;
                    } else if (id == R.id.navigation_exercises) {
                        Toast.makeText(addDishesActivity.this, "Redirecting to home activity", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (id == R.id.navigation_nutrition) {
                        Intent intent = new Intent(addDishesActivity.this, Diet_activity.class);
                        startActivity(intent);
                        return true;
                    } else if (id == R.id.navigation_journal) {
                        Toast.makeText(addDishesActivity.this, "Redirecting to profile", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (id == R.id.navigation_profile) {
                        Toast.makeText(addDishesActivity.this, "Redirecting to profile", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }
            };
}