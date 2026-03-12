package com.example.test.ui.events.listEvents;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;
import com.example.test.data.network.dto.EventResponse;
import com.example.test.data.session.SessionManager;
import com.example.test.ui.events.mapEvents.MapEventsActivity;
import com.example.test.ui.navigation_helper.NavigationHelperActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListEventsActivity extends AppCompatActivity {

    private TextView btnTabMap;
    private EventAdapter adapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_events);

        btnTabMap = findViewById(R.id.btnTabMap);
        btnTabMap.setOnClickListener(v -> {
            Intent intent = new Intent(ListEventsActivity.this, MapEventsActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        NavigationHelperActivity.setupBottomNavigation(this, R.id.nav_events);

        apiService = ApiClient.create(new SessionManager(this));

        RecyclerView rvEvents = findViewById(R.id.rvEvents);
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(this::showEventDialog);
        rvEvents.setAdapter(adapter);

        loadEvents();
    }

    private void loadEvents() {
        apiService.events().enqueue(new Callback<List<EventResponse>>() {
            @Override
            public void onResponse(Call<List<EventResponse>> call, Response<List<EventResponse>> response) {
                if (response.isSuccessful()) {
                    adapter.submit(response.body());
                } else {
                    Toast.makeText(ListEventsActivity.this, R.string.events_load_failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EventResponse>> call, Throwable t) {
                Toast.makeText(ListEventsActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEventDialog(EventResponse event) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_event_details);

        TextView title = dialog.findViewById(R.id.tvDetailTitle);
        TextView description = dialog.findViewById(R.id.tvDetailDescription);
        TextView address = dialog.findViewById(R.id.tvDetailAddress);
        TextView date = dialog.findViewById(R.id.tvDetailDate);

        title.setText(valueOrDefault(event.resolvedTitle(), "Событие"));
        description.setText(valueOrDefault(event.description, "Описание не указано"));
        address.setText(valueOrDefault(event.address, "Адрес не указан"));
        date.setText(valueOrDefault(event.resolvedDate(), "Дата не указана"));

        View close = dialog.findViewById(R.id.btnCloseDetails);
        View btnEnroll = dialog.findViewById(R.id.btnEnroll);
        View btnShowUsers = dialog.findViewById(R.id.btnShowUsers);

        close.setOnClickListener(v -> dialog.dismiss());
        btnShowUsers.setOnClickListener(v -> Toast.makeText(this, R.string.participants_open, Toast.LENGTH_SHORT).show());
        btnEnroll.setOnClickListener(v -> {
            if (event.id == null) {
                Toast.makeText(this, R.string.event_join_failed, Toast.LENGTH_SHORT).show();
                return;
            }

            apiService.joinEvent(event.id).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ListEventsActivity.this, R.string.event_joined, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(ListEventsActivity.this, R.string.event_join_failed, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ListEventsActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }

    private String valueOrDefault(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value;
    }
}
