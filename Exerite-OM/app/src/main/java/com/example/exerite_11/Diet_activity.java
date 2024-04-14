package com.example.exerite_11;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Diet_activity extends AppCompatActivity {
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        userEmail = getIntent().getStringExtra("USER_EMAIL");

    }

    public void image_non_veg(View view) {
        // Handle click on non-veg image here
        Intent intent = new Intent(this, NonVeg_Activity.class);
        startActivity(intent);
    }
    public void image_veg(View view) {
        // Handle click on non-veg image here
        Intent intent = new Intent(this, NonVeg_Activity.class);
        startActivity(intent);
    }
    public void image_drinks(View view) {
        // Handle click on non-veg image here
        Intent intent = new Intent(this, NonVeg_Activity.class);
        startActivity(intent);
    }
}

