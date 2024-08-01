package com.example.exerite_11;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        RecyclerView recyclerView = findViewById(R.id.notificationRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<NotificationModel> notificationList = new ArrayList<>();
        // Add your notifications to the list here
        // For example:
        notificationList.add(new NotificationModel("Morning Exercise", "07:00 AM"));
        notificationList.add(new NotificationModel("Healthy Lunch", "12:00 PM"));
        // Add more notifications...

        NotificationAdapter adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);
    }
}