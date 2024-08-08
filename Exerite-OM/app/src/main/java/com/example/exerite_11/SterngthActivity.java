package com.example.exerite_11;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class SterngthActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExersiseRvAdapter adapter;
    private SearchView searchView;
    String email;
    private ImageView arrowbtn;
    private ArrayList<ExersiseModel> exerciseModels = new ArrayList<>();
    private DBHelper dbHelper; // Database helper instance
    private String category = "Strength";
    Button addbtn;// Default category for strength exercises

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sterngth);
        addbtn=findViewById(R.id.ex_add_btn);
        searchView = findViewById(R.id.ex_SV);
        arrowbtn = findViewById(R.id.arrowbtn);
    email=Login_activity.getUserEmail(SterngthActivity.this);
        // Initialize the database helper
        dbHelper = new DBHelper(this);

        // Back button functionality
        arrowbtn.setOnClickListener(v -> finish());
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddExerciseDialog();
            }
        });
        // Set up the search view
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.ex_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load data from the database
        loadExerciseDataFromDatabase();

        // Initialize and set the adapter
        adapter = new ExersiseRvAdapter(this, exerciseModels);
        recyclerView.setAdapter(adapter);

        // Button to add a new exercise
        Button addExerciseButton = findViewById(R.id.ex_add_btn);
        addExerciseButton.setOnClickListener(v -> showAddExerciseDialog());
    }

    private void loadExerciseDataFromDatabase() {
        // Clear the existing data
        exerciseModels.clear();

        // Fetch data from the database and add it to the list
        ArrayList<ExersiseModel> dbExerciseModels = dbHelper.getExercises(email, category);
        if (dbExerciseModels != null && !dbExerciseModels.isEmpty()) {
            exerciseModels.addAll(dbExerciseModels);
        }

        // Notify the adapter of the data change
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void filterList(String text) {
        ArrayList<ExersiseModel> filteredList = new ArrayList<>();
        for (ExersiseModel item : exerciseModels) {
            if (item.getWorkout_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }

    private void showAddExerciseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_diet, null);
        builder.setView(dialogView);

        EditText editTextExerciseName = dialogView.findViewById(R.id.editTextDishName);
        EditText editTextExerciseDescription = dialogView.findViewById(R.id.editTextCalories);

        builder.setTitle("Add New Exercise")
                .setPositiveButton("Add", (dialog, which) -> {
                    String exerciseName = editTextExerciseName.getText().toString().trim();
                    String exerciseDescription = editTextExerciseDescription.getText().toString().trim();

                    if (exerciseName.isEmpty() || exerciseDescription.isEmpty()) {
                        Toast.makeText(SterngthActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        // Save the new exercise to the database
                        saveNewExercise(exerciseName, exerciseDescription);
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveNewExercise(String exerciseName, String exerciseDescription) {
        // Insert the new exercise into the database
        boolean isInserted = dbHelper.addExercise(email, exerciseName, exerciseDescription, category);

        if (isInserted) {
            Toast.makeText(this, "Exercise added successfully", Toast.LENGTH_SHORT).show();
            // Reload the data from the database and update the RecyclerView
            loadExerciseDataFromDatabase();
        } else {
            Toast.makeText(this, "Failed to add exercise", Toast.LENGTH_SHORT).show();
        }
    }
}
