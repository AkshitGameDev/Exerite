package com.example.exerite_11;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class JournalFragment extends Fragment {

    private RecyclerView journalRV;
    private SearchView searchView;
    private Button addJournalButton;
    private JournalRvAdapter journalAdapter;
    private ArrayList<JournalModel> journalData;
    private ArrayList<JournalModel> filteredData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_journal_view, container, false);

        // Initialize the SearchView, RecyclerView, and Button
        searchView = view.findViewById(R.id.ex_SV);
        journalRV = view.findViewById(R.id.ex_RV);
        addJournalButton = view.findViewById(R.id.ex_add_btn);

        journalData = new ArrayList<>();
        filteredData = new ArrayList<>();

        // Setup RecyclerView and its adapter
        journalAdapter = new JournalRvAdapter(getContext(), filteredData, position -> {
            if (position < filteredData.size()) {
                JournalModel journal = filteredData.get(position);
                Intent intent = new Intent(getContext(), AddJournalActivity.class);
                intent.putExtra("journalId", journal.getId()); // Pass the correct journal ID
                startActivity(intent);
            } else {
                // Handle case where position is out of bounds
                Intent intent = new Intent(getContext(), AddJournalActivity.class);
                startActivity(intent);
            }
        });

        // Set the layout manager for the RecyclerView
        journalRV.setLayoutManager(new LinearLayoutManager(getContext()));
        journalRV.setAdapter(journalAdapter);

        // Load journals from the database
        loadJournals();

        // Set an OnClickListener for the FloatingActionButton to add a new journal
        addJournalButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddJournalActivity.class);
            startActivity(intent);
        });

        // Set an OnQueryTextListener for the SearchView to filter journals
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterJournals(newText);
                return true;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload journals when the fragment resumes
        loadJournals();
    }

    // Load journals from the database and update the adapter
    private void loadJournals() {
        DBHelper databaseManager = new DBHelper(getContext());
        journalData = databaseManager.getAllJournals();

        /*Log each journal entry
        for (JournalModel journal : journalData) {
            Log.d("JournalFragment", "Loaded Journal: " + journal.getTitle() + ", " + journal.getDescription());
        }

         */

        filteredData.clear();
        filteredData.addAll(journalData);
        journalAdapter.updateList(filteredData);
    }

    // Filter journals based on the search query
    private void filterJournals(String searchText) {
        if (TextUtils.isEmpty(searchText)) {
            filteredData.clear();
            filteredData.addAll(journalData);
        } else {
            List<JournalModel> filteredList = new ArrayList<>();
            for (JournalModel journal : journalData) {
                if (journal.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredList.add(journal);
                }
            }
            filteredData.clear();
            filteredData.addAll(filteredList);
        }
        journalAdapter.updateList(filteredData);
    }
}
