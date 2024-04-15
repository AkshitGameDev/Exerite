package com.example.exerite_11;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class VegActivity extends AppCompatActivity {

    SearchView searchView;
    ArrayList<DietModel> dietModels = new ArrayList<>();
    RecyclerView recyclerView;
    GenericDietRVAdapter adapter;

    private void SetupDietModels(){
        String[] Name = getResources().getStringArray(R.array.veg_food_names); //change
        String[] Cal = getResources().getStringArray(R.array.veg_food_calories);//change

        for( int i = 0; i < Name.length; i++){
            dietModels.add(new DietModel(Name[i], Cal[i]));
        }
    }
    private void fileList(String text) {
        ArrayList<DietModel> filterList = new ArrayList<>();
        for (DietModel item : dietModels) {
            if (item.getDish().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(this, "No data Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.seTilteredList(filterList);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veg);
        recyclerView = findViewById(R.id.VegRV);
        SetupDietModels();

        adapter = new GenericDietRVAdapter(this, dietModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.VegS);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }


        });
    }
}