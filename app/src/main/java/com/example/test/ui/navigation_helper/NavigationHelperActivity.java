package com.example.test.ui.navigation_helper;

import android.app.Activity;
import android.content.Intent;

import com.example.test.R;
import com.example.test.ui.events.ChatActivity;
import com.example.test.ui.events.ProfileActivity;
import com.example.test.ui.events.listEvents.ListEventsActivity;
import com.example.test.ui.main.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationHelperActivity {

    public static void setupBottomNavigation(Activity activity, int selectedItemId) {

        BottomNavigationView bottomNavigationView =
                activity.findViewById(R.id.bottom_navigation);

        if (bottomNavigationView == null) return;

        bottomNavigationView.setSelectedItemId(selectedItemId);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
                return true;
            }

            if (id == R.id.nav_events) {
                activity.startActivity(new Intent(activity, ListEventsActivity.class));
                activity.finish();
                return true;
            }

            if (id == R.id.nav_chat) {
                activity.startActivity(new Intent(activity, ChatActivity.class));
                activity.finish();
                return true;
            }

            if (id == R.id.nav_profile) {
                activity.startActivity(new Intent(activity, ProfileActivity.class));
                activity.finish();
                return true;
            }

            return false;
        });
    }
}