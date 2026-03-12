package com.example.test.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

public class ProfileEditActivity extends AppCompatActivity {
    Button btnSaveChanges;
    Button btnCancelChanges;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnCancelChanges = findViewById(R.id.btnCancelChanges);
        btnSaveChanges.setOnClickListener(v -> {

        });
        btnCancelChanges.setOnClickListener(v -> {

        });
    }
}
