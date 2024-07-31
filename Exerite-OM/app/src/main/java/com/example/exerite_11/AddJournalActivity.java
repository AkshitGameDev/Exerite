package com.example.exerite_11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class AddJournalActivity extends AppCompatActivity {
    private EditText txtTitle;
    private EditText txtDescription;
    private Integer journalId;
    private String useremails;
    DBHelper dbHelper;
   //2 private JournalModel journalModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_journal);
        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);

        Button addbtn = findViewById(R.id.btnDone);
        dbHelper =new DBHelper(this);
        Toolbar toolbar = findViewById(R.id.AddJournaltoolbar);
        setSupportActionBar(toolbar);
        useremails= Login_activity.getUserEmail(this);
        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("journalId")) {
            journalId = intent.getIntExtra("journalId", -1);
            if (journalId != -1) {
                loadJournalData(journalId);
            }
        }
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = txtTitle.getText().toString().trim();
                String description = txtDescription.getText().toString().trim();

                if (!title.isEmpty() && !description.isEmpty()) {
                    if (journalId != null) {
                        // Update existing journal
                        dbHelper.updateJournal(journalId, title, description,useremails);
                    } else {
                        // Add new journal
                        dbHelper.insertJournal(title, description,useremails);
                    }
                    finish();
                } else {
                    Toast.makeText(AddJournalActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle toolbar back button click
        finish(); // Go back to the previous activity/fragment
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadJournalData(Integer journalId) {
        JournalModel journal = dbHelper.getJournalById(journalId);
        if (journal != null) {
            txtTitle.setText(journal.getTitle());
            txtDescription.setText(journal.getDescription());
        }
    }

    }