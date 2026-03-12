package com.example.test.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.ui.events.listEvents.ListEventsActivity;
import com.example.test.ui.navigation_helper.NavigationHelperActivity;
import com.example.test.ui.news.NewsActivity;

public class MainActivity extends AppCompatActivity {
    LinearLayout btnEvents;
    LinearLayout btnPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        NavigationHelperActivity.setupBottomNavigation(this, R.id.nav_home);

        btnEvents = findViewById(R.id.btnEvents);
        btnPosts = findViewById(R.id.btnPosts);

        btnEvents.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListEventsActivity.class);
            startActivity(intent);
        });

        btnPosts.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(intent);
        });
    }
}