package com.example.test.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

public class NewsDetailActivity extends AppCompatActivity {

    ImageView btnCloseDetailsNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_detail);

        btnCloseDetailsNews = findViewById(R.id.btnCloseNewsDetail);
        btnCloseDetailsNews.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewsActivity.class);
        });
    }
}
