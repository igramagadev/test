package com.example.test.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.data.session.SessionManager;
import com.example.test.ui.auth.LoginActivity;
import com.example.test.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SessionManager sessionManager = new SessionManager(this);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Class<?> target = sessionManager.isLoggedIn() ? MainActivity.class : LoginActivity.class;
            startActivity(new Intent(SplashActivity.this, target));
            finish();
        }, SPLASH_TIME);
    }
}
