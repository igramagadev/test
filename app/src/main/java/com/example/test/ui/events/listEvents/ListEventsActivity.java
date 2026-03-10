package com.example.test.ui.events.listEvents;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.ui.navigation_helper.NavigationHelperActivity;

public class ListEventsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_events);

        NavigationHelperActivity.setupBottomNavigation(this, R.id.nav_events);
    }
}
