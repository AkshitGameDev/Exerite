package com.example.exerite_11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.exerite_11.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_Activity extends AppCompatActivity {
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Schedule daily notifications
        NotificationScheduler.scheduleDailyNotifications(this);

        // Initialize with HomeFragment
        replaceFragment(new HomeFragment());

        // Set listener for bottom navigation items
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.navigation_exercises) {
                replaceFragment(new ExerciseFragment());
            } else if (itemId == R.id.navigation_nutrition) {
                replaceFragment(new diet_fragment());
            } else if (itemId == R.id.navigation_journal) {
                replaceFragment(new JournalFragment());
            } else if (itemId == R.id.navigation_profile) {
                replaceFragment(new SettingsActiity());
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void image_exersise(View view) {
        replaceFragment(new ExerciseFragment());
    }

    public void image_diet(View view) {
        replaceFragment(new diet_fragment());
    }

    public void image_journal(View view) {
        replaceFragment(new JournalFragment());
    }

    public void image_strength(View view) {
        startNewActivity(SterngthActivity.class);
    }

    public void image_cardio(View view) {
        startNewActivity(CardioActivity.class);
    }
/*
    public void image_nonVeg(View view) {
        startNewActivity(NonVegActivity.class);
    }

    public void image_Veg(View view) {
        startNewActivity(VegActivity.class);
    }

    public void image_Drinks(View view) {
        startNewActivity(DrinksActivity.class);
    }

*/
    // Helper method to start a new activity
    private void startNewActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }


}
