package com.example.test.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.example.test.ui.main.MainActivity;

public class NewsActivity extends AppCompatActivity {
    ImageView BtnCloseNews;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news);

        BtnCloseNews = findViewById(R.id.btnCloseNews);

        BtnCloseNews.setOnClickListener( view -> {
            Intent intent = new Intent(NewsActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}

