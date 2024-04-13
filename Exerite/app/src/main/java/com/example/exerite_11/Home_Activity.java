package com.example.exerite_11;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class Home_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView nothingToDisplayTextView;
    private List<String> recentWorkoutsList;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve user email from intent
        userEmail = getIntent().getStringExtra("USER_EMAIL");

//        recyclerView = findViewById(R.id.recyclerView);
//        nothingToDisplayTextView = findViewById(R.id.nothingToDisplayTextView);

//        recentWorkoutsList = new ArrayList<>();
//        recentWorkoutsList.add("Workout 1");
//        recentWorkoutsList.add("Workout 2");
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
        // MyAdapter adapter = new MyAdapter(recentWorkoutsList);
        // recyclerView.setAdapter(adapter);

        // if (recentWorkoutsList.isEmpty()) {
        //     recyclerView.setVisibility(View.GONE);
        //     nothingToDisplayTextView.setVisibility(View.VISIBLE);
        // } else {
        //     recyclerView.setVisibility(View.VISIBLE);
        //     nothingToDisplayTextView.setVisibility(View.GONE);
        // }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.navigation_home) {
                        return true;
                    } else if (id == R.id.navigation_exercises) {
//                        // Display user email
                        Toast.makeText(Home_Activity.this, "Welcome! " + userEmail, Toast.LENGTH_SHORT).show();
                        return true;
//                        Intent intent = new Intent(Home_Activity.this, add_exercise_activity.class);
//                        intent.putExtra("USER_EMAIL", userEmail);
//                        startActivity(intent);
//                        return true;
                    } else if (id == R.id.navigation_nutrition) {
//                        Toast.makeText(Home_Activity.this, "Redirecting to profile", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Home_Activity.this, Diet_activity.class);
                        intent.putExtra("USER_EMAIL", userEmail);
                        startActivity(intent);
                        return true;
                    } else if (id == R.id.navigation_progress) {
                        Toast.makeText(Home_Activity.this, "Redirecting to profile", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (id == R.id.navigation_profile) {
                        Toast.makeText(Home_Activity.this, "Redirecting to profile", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }
            };
}
