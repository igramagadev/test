package com.example.test.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;
import com.example.test.data.network.dto.ProfileResponse;
import com.example.test.data.session.SessionManager;
import com.example.test.ui.auth.LoginActivity;
import com.example.test.ui.events.CreateEventActivity;
import com.example.test.ui.navigation_helper.NavigationHelperActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        NavigationHelperActivity.setupBottomNavigation(this, R.id.nav_profile);

        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        Button btnCreateEvents = findViewById(R.id.btnCreateEvent);
        Button btnLogout = findViewById(R.id.btnLogout);

        btnEditProfile.setOnClickListener(view -> startActivity(new Intent(ProfileActivity.this, ProfileEditActivity.class)));
        btnCreateEvents.setOnClickListener(view -> startActivity(new Intent(ProfileActivity.this, CreateEventActivity.class)));
        btnLogout.setOnClickListener(view -> {
            new SessionManager(this).clear();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finishAffinity();
        });

        loadProfile();
    }

    private void loadProfile() {
        ApiService apiService = ApiClient.create(new SessionManager(this));
        apiService.profile().enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProfileResponse p = response.body();
                    TextView tvProfileName = findViewById(R.id.tvProfileName);
                    TextView tvProfileBadge = findViewById(R.id.tvProfileBadge);
                    TextView tvInfoName = findViewById(R.id.tvInfoName);
                    TextView tvInfoEdu = findViewById(R.id.tvInfoEdu);
                    TextView tvInfoEmail = findViewById(R.id.tvInfoEmail);

                    String fullName = valueOrDefault(p.fullName != null ? p.fullName : p.name, getString(R.string.main_name));
                    tvProfileName.setText(fullName);
                    tvInfoName.setText(fullName);
                    tvProfileBadge.setText(valueOrDefault(p.role, getString(R.string.main_position)));
                    tvInfoEdu.setText(valueOrDefault(p.school, getString(R.string.main_school)));
                    tvInfoEmail.setText(valueOrDefault(p.email, getString(R.string.main_email)));
                } else {
                    android.widget.Toast.makeText(ProfileActivity.this, R.string.profile_load_failed, android.widget.Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                android.widget.Toast.makeText(ProfileActivity.this, R.string.network_error, android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String valueOrDefault(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value;
    }
}
