package com.example.exerite_11;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class JournalFragment extends Fragment {

    private RecyclerView journalRV;
    private SearchView searchView;
    private Button addJournalButton;
    private JournalRvAdapter journalAdapter;
    private List<JournalModel> journalData;
    private List<JournalModel> filteredData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_journal_view, container, false);

        // Initialize the SearchView, RecyclerView, and Button
        searchView = view.findViewById(R.id.searchEditText);
        journalRV = view.findViewById(R.id.journalRV);
        addJournalButton = view.findViewById(R.id.journal_add_button);

        journalData = new ArrayList<>();
        filteredData = new ArrayList<>();

        // Setup RecyclerView and its adapter
        journalAdapter = new JournalRvAdapter(getContext(), filteredData, position -> {
            if (position == 0) {
                // Add Journal item clicked, navigate to AddJournalActivity
                Intent intent = new Intent(getContext(), AddJournalActivity.class);
                startActivity(intent);
            } else {
                // Existing Journal item clicked, open for editing
                JournalModel journal = filteredData.get(position - 1);
                Intent intent = new Intent(getContext(), AddJournalActivity.class);
                intent.putExtra("journal", journal);
                startActivity(intent);
            }
        });

        // Set the layout manager for the RecyclerView
        journalRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
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
        DBHelper databaseManager = new DBHelper();
        journalData = databaseManager.getAllJournals();
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
