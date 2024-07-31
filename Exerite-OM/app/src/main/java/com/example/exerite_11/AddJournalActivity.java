package com.example.exerite_11;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    private String useremails;
    DBHelper dbHelper;
   //2 private JournalModel journalModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_journal);
        Button addbtn = findViewById(R.id.btnDone);

        Toolbar toolbar = findViewById(R.id.AddJournaltoolbar);
        setSupportActionBar(toolbar);
        useremails= Login_activity.getUserEmail(this);
        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DBHelper(AddJournalActivity.this);
                // Retrieve all journals
                dbHelper.insertJournal(txtTitle.toString(),txtDescription.toString(),useremails);
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

    }



}