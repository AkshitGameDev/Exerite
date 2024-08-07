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

public class NonVegActivity extends AppCompatActivity {

    private SearchView searchView;
    private ArrayList<DietModel> dietModels = new ArrayList<>();
    private RecyclerView recyclerView;
    private GenericDietRVAdapter adapter;
    private ImageView nvarrowbtn;
    private Button addDishButton;
    private String email, category;
    private DBHelper dbHelper; // Database helper instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_veg2);

        nvarrowbtn = findViewById(R.id.arrowbtn);
        addDishButton = findViewById(R.id.ex_add_btn);

        // Initialize the database helper
        dbHelper = new DBHelper(this);

        // Retrieve the user email and category
        email = Login_activity.getUserEmail(NonVegActivity.this);
        category = "NonVeg"; // Change category to NonVeg

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.ex_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load data from the database
        loadDietDataFromDatabase();

        // Initialize and set the adapter
        adapter = new GenericDietRVAdapter(this, dietModels, new GenericDietRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        // Back button functionality
        nvarrowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the activity
            }
        });

        // Set up the search view
        searchView = findViewById(R.id.ex_SV);
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

        // Button to add a new dish
        addDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDishDialog();
            }
        });
    }

    private void loadDietDataFromDatabase() {
        // Clear the existing data
        dietModels.clear();

        // Fetch data from the database and add it to the list
        ArrayList<DietModel> dbDietModels = dbHelper.getDiets(email, category);
        if (dbDietModels != null && !dbDietModels.isEmpty()) {
            dietModels.addAll(dbDietModels);
        }

        // Notify the adapter of the data change
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void filterList(String text) {
        ArrayList<DietModel> filteredList = new ArrayList<>();
        for (DietModel item : dietModels) {
            if (item.getDish().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }

    private void showAddDishDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_diet, null);
        builder.setView(dialogView);

        EditText editTextDishName = dialogView.findViewById(R.id.editTextDishName);
        EditText editTextCalories = dialogView.findViewById(R.id.editTextCalories);

        builder.setTitle("Add New Dish")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String dishName = editTextDishName.getText().toString().trim();
                        String calories = editTextCalories.getText().toString().trim();

                        if (dishName.isEmpty() || calories.isEmpty()) {
                            Toast.makeText(NonVegActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        } else {
                            // Save the new dish to the database
                            saveNewDish(dishName, calories);
                        }
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveNewDish(String dishName, String calories) {
        // Create a new DietModel object
        DietModel newDiet = new DietModel(email, category, dishName, calories);

        // Insert the new diet into the database
        boolean isInserted = dbHelper.addDiet(email, category, dishName, calories);

        if (isInserted) {
            Toast.makeText(this, "Dish added successfully", Toast.LENGTH_SHORT).show();
            // Reload the data from the database and update the RecyclerView
            loadDietDataFromDatabase();
        } else {
            Toast.makeText(this, "Failed to add dish", Toast.LENGTH_SHORT).show();
        }
    }
}
