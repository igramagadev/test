package com.example.test.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;
import com.example.test.data.network.dto.ProfileResponse;
import com.example.test.data.session.SessionManager;
import com.example.test.ui.events.listEvents.ListEventsActivity;
import com.example.test.ui.navigation_helper.NavigationHelperActivity;
import com.example.test.ui.news.NewsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView tvName;
    private TextView tvBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        NavigationHelperActivity.setupBottomNavigation(this, R.id.nav_home);

        LinearLayout btnEvents = findViewById(R.id.btnEvents);
        LinearLayout btnPosts = findViewById(R.id.btnPosts);
        tvName = findViewById(R.id.tvName);
        tvBadge = findViewById(R.id.tvBadge);

        btnEvents.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ListEventsActivity.class)));
        btnPosts.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NewsActivity.class)));

        loadProfile();
    }

    private void loadProfile() {
        ApiService apiService = ApiClient.create(new SessionManager(this));
        apiService.profile().enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProfileResponse profile = response.body();
                    String name = profile.fullName != null ? profile.fullName : profile.name;
                    if (name != null && !name.trim().isEmpty()) {
                        tvName.setText(name);
                    }
                    if (profile.role != null && !profile.role.trim().isEmpty()) {
                        tvBadge.setText(profile.role);
                    }
                } else {
                    Toast.makeText(MainActivity.this, R.string.profile_load_failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
