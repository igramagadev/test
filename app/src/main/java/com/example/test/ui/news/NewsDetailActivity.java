package com.example.test.ui.news;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_detail);

        ImageView btnCloseDetailsNews = findViewById(R.id.btnCloseNewsDetail);
        TextView tvTitle = findViewById(R.id.tvNewsDetailTitle);
        TextView tvBody = findViewById(R.id.tvNewsDetailText);

        String title = getIntent().getStringExtra("title");
        String body = getIntent().getStringExtra("body");

        tvTitle.setText(title == null || title.trim().isEmpty() ? getString(R.string.news) : title);
        tvBody.setText(body == null || body.trim().isEmpty() ? getString(R.string.empty_news_body) : body);

        btnCloseDetailsNews.setOnClickListener(v -> finish());
    }
}
