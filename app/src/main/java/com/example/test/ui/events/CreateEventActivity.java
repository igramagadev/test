package com.example.test.ui.events;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

public class CreateEventActivity extends AppCompatActivity {

    Button btnSubmitEvent;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        btnSubmitEvent = findViewById(R.id.btnSubmitEvent);
        btnSubmitEvent.setOnClickListener(v -> {

        });
    }
}
