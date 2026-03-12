package com.example.test.ui.events.listEvents;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.ui.events.mapEvents.MapEventsActivity;
import com.example.test.ui.navigation_helper.NavigationHelperActivity;

public class ListEventsActivity extends AppCompatActivity {

    private TextView btnTabMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_events);

        btnTabMap = findViewById(R.id.btnTabMap);

        btnTabMap.setOnClickListener(v -> {
            Intent intent = new Intent(ListEventsActivity.this, MapEventsActivity.class);
            startActivity(intent);
        });

        NavigationHelperActivity.setupBottomNavigation(this, R.id.nav_events);
    }
}