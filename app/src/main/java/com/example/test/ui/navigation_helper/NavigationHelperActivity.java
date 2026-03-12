package com.example.test.ui.navigation_helper;

import android.app.Activity;
import android.content.Intent;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.test.R;
import com.example.test.ui.chat.ChatActivity;
import com.example.test.ui.events.listEvents.ListEventsActivity;
import com.example.test.ui.main.MainActivity;
import com.example.test.ui.profile.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationHelperActivity {

    public static void setupBottomNavigation(Activity activity, int selectedItemId) {

        BottomNavigationView bottomNavigationView =
                activity.findViewById(R.id.bottom_navigation);

        if (bottomNavigationView == null) return;

        int initialBottomPadding = bottomNavigationView.getPaddingBottom();
        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigationView, (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(
                    view.getPaddingLeft(),
                    view.getPaddingTop(),
                    view.getPaddingRight(),
                    initialBottomPadding + systemBars.bottom
            );
            return insets;
        });

        bottomNavigationView.setSelectedItemId(selectedItemId);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();
            if (id == selectedItemId) {
                return true;
            }

            if (id == R.id.nav_home) {
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }

            if (id == R.id.nav_events) {
                activity.startActivity(new Intent(activity, ListEventsActivity.class));
                activity.finish();
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }

            if (id == R.id.nav_chat) {
                activity.startActivity(new Intent(activity, ChatActivity.class));
                activity.finish();
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }

            if (id == R.id.nav_profile) {
                activity.startActivity(new Intent(activity, ProfileActivity.class));
                activity.finish();
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }

            return false;
        });
    }
}
