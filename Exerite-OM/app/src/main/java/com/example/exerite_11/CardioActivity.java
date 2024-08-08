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

public class CardioActivity extends AppCompatActivity {
    ArrayList<CardioModel> cardioModels;
    RecyclerView recyclerView;
    CardioRvAdapter adapter;

    private SearchView searchView;

    ImageView arrowbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio);
        searchView = findViewById(R.id.ex_SV);
        searchView.clearFocus();
        arrowbtn=findViewById(R.id.arrowbtn);

        arrowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

        recyclerView = findViewById(R.id.ex_RV);

        cardioModels = new ArrayList<>(); // Initialize cardioModels here

        setupCardioModels();

        adapter = new CardioRvAdapter(this, cardioModels);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupCardioModels(){
        String[] carName = getResources().getStringArray(R.array.cardio_exercise_names);
        String[] carTime = getResources().getStringArray(R.array.cardio_exercise_times);

        for (int i = 0; i< carName.length; i++){
            cardioModels.add(new CardioModel(carName[i], carTime[i] + " mins"));
        }
    }
    private void fileList(String text) {
        ArrayList<CardioModel> filterList = new ArrayList<>();
        for (CardioModel item : cardioModels) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(this, "No data Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.seTilteredList(filterList);
        }
    }
}
