package com.example.test.ui.events.mapEvents;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.ui.events.listEvents.ListEventsActivity;
import com.example.test.ui.navigation_helper.NavigationHelperActivity;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class MapEventsActivity extends AppCompatActivity {

    private MapView mapView;
    private TextView btnTabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);

        btnTabList = findViewById(R.id.btnTabList);
        btnTabList.setOnClickListener(v -> {
            Intent intent = new Intent(MapEventsActivity.this, ListEventsActivity.class);
            startActivity(intent);
        });

        NavigationHelperActivity.setupBottomNavigation(this, R.id.nav_events);

        mapView = findViewById(R.id.mapContainer);

        Point ufa = new Point(54.7388, 55.9721);

        mapView.getMap().move(
                new CameraPosition(ufa, 12.0f, 0.0f, 0.0f)
        );

        mapView.getMap().getMapObjects().addEmptyPlacemark(ufa);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}