package com.example.exerite_11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class EditProfile extends AppCompatActivity {


    Button SaveChanges;
    EditText NewName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        NewName = findViewById(R.id.textField);
         SaveChanges = findViewById(R.id.saveChanges);
        SaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the EditText is not empty
                if(!NewName.getText().toString().isEmpty()) {
                    // If not empty, save changes
                    SavingChanges(NewName.getText().toString());
                } else {
                    // If empty, maybe show an error message to the user
                    NewName.setError("Name cannot be empty");
                }
            }
        });
    }

    public  void  SavingChanges(String _name){
        SettingsActiity.name.setText("Hey, " + _name.substring(0, 4) + "..." + "!");
        HomeFragment.name.setText("Hey, " + _name.substring(0, 4) + "...");
        startActivity(new Intent(EditProfile.this , SettingsActiity.class));
    }
}