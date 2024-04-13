package com.example.exerite_11;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NonVeg_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_veg);

        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // Floating Action Button
        FloatingActionButton fabNonVeg = findViewById(R.id.floatingActionButton2);
        if (fabNonVeg != null) {
            fabNonVeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform the action when the floating action button is clicked
                    // For example, navigate to another activity
                    Intent intent = new Intent(NonVeg_Activity.this, addDishesActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.navigation_home) {
                        Intent intent = new Intent(NonVeg_Activity.this, Home_Activity.class);
                        startActivity(intent);
                        return true;
                    } else if (id == R.id.navigation_exercises) {
                        Toast.makeText(NonVeg_Activity.this, "Redirecting to home activity", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (id == R.id.navigation_nutrition) {
                        Intent intent = new Intent(NonVeg_Activity.this, Diet_activity.class);
                        startActivity(intent);
                        return true;
                    } else if (id == R.id.navigation_progress) {
                        Toast.makeText(NonVeg_Activity.this, "Redirecting to profile", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (id == R.id.navigation_profile) {
                        Toast.makeText(NonVeg_Activity.this, "Redirecting to profile", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }
            };
}
