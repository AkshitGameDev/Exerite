package com.example.exerite_11;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class VegActivity extends AppCompatActivity {

    SearchView searchView;
    ArrayList<DietModel> dietModels = new ArrayList<>();
    RecyclerView recyclerView;
    GenericDietRVAdapter adapter;

    ImageView arrowbtn;


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
        arrowbtn =findViewById(R.id.arrowbtn);


        recyclerView = findViewById(R.id.ex_RV);
        SetupDietModels();


        adapter = new GenericDietRVAdapter(this, dietModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchView = findViewById(R.id.ex_SV);
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