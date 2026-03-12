package com.example.test.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.ui.events.CreateEventActivity;
import com.example.test.ui.navigation_helper.NavigationHelperActivity;

public class ProfileActivity extends AppCompatActivity {

    Button btnEditProfile;
    Button btnCreateEvents;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        NavigationHelperActivity.setupBottomNavigation(this, R.id.nav_profile);

        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnCreateEvents = findViewById(R.id.btnCreateEvent);
        btnLogout = findViewById(R.id.btnLogout);


        btnEditProfile.setOnClickListener(View -> {
            Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
            startActivity(intent);
        });
        btnCreateEvents.setOnClickListener(View -> {
            Intent intent = new Intent(ProfileActivity.this, CreateEventActivity.class);
            startActivity(intent);
        });
        btnLogout.setOnClickListener(View -> {

        });
    }
}
