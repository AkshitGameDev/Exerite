package com.example.exerite_11;

import android.app.Activity;
import android.content.Intent;
import com.example.exerite_11.R;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerite_11.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;



import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

public class Home_Activity extends AppCompatActivity {
    TextView name;
    ActivityHomeBinding binding;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        userEmail = getIntent().getStringExtra("USER_EMAIL");
        replaceFragment(new HomeFragment());
        binding.bottomNavigation.setOnItemSelectedListener( item -> {

            if(item.getItemId() == R.id.navigation_home){
                Toast.makeText(this, "Rediericting to home screen ", Toast.LENGTH_SHORT).show();
                replaceFragment(new HomeFragment());
            }
            else if(item.getItemId() == R.id.navigation_exercises){

                Toast.makeText(this, "Rediericting to exersise screen ", Toast.LENGTH_SHORT).show();
                replaceFragment(new ExerciseFragment());
            }
            else if(item.getItemId() == R.id.navigation_nutrition){

                Toast.makeText(this, "Rediericting to diet screen ", Toast.LENGTH_SHORT).show();
                replaceFragment(new HomeFragment());
            }
            else if(item.getItemId() == R.id.navigation_journal){

                Toast.makeText(this, "Rediericting to journal screen ", Toast.LENGTH_SHORT).show();
                replaceFragment(new HomeFragment());
            }
            else if(item.getItemId() == R.id.navigation_profile){

                Toast.makeText(this, "Rediericting to profile screen ", Toast.LENGTH_SHORT).show();
                replaceFragment(new HomeFragment());
            }
            return true;
        });

    }

    public void getfragment(Fragment fragment) {
        replaceFragment(fragment);
    }
     private void  replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }



    public void image_exersise(View view){
        Toast.makeText(this, "Rediericting to exersise screen ", Toast.LENGTH_SHORT).show();
        replaceFragment(new ExerciseFragment());
    }
    public void image_diet(View view){
        Toast.makeText(this, "Rediericting to diet screen ", Toast.LENGTH_SHORT).show();
    }
    public void image_journal(View view){
        Toast.makeText(this, "Rediericting to journlal screen ", Toast.LENGTH_SHORT).show();
    }
    public void image_strength(View view) {
        Intent intent = new Intent(this, SterngthActivity.class);
        startActivity(intent);
    }
    public void image_cardio(View view) {
        Intent intent = new Intent(this, CardioActivity.class);
        startActivity(intent);
    }

}
