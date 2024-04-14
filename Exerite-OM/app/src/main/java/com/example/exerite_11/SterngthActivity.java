package com.example.exerite_11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SterngthActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ExersiseRvAdapter adapter;
    private SearchView searchView;
    ArrayList<ExersiseModel> ExersiseModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sterngth);
        searchView = findViewById(R.id.strenghtSearch);
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

        recyclerView = findViewById(R.id.strenghtRv);
        setupExersiseModels();

        adapter = new ExersiseRvAdapter(this, ExersiseModels);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private Void setupExersiseModels(){
        String[] exeName = getResources().getStringArray(R.array.strength_exercise_names);
        String[] exeRep = getResources().getStringArray(R.array.strength_exercise_reps);

        for (int i = 0; i< exeName.length; i++){
            ExersiseModels.add(new ExersiseModel(exeName[i], exeRep[i] + " reps"));
        }
        return null;
    }
    private void fileList(String text) {
        ArrayList<ExersiseModel> filterList = new ArrayList<>();
        for (ExersiseModel item : ExersiseModels ){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        if(filterList.isEmpty()){
            Toast.makeText(this, "No data Found", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter.seTilteredList(filterList);
        }
    }
}