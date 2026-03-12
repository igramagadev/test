package com.example.test.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;
import com.example.test.data.network.dto.PostResponse;
import com.example.test.data.session.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news);

        ImageView btnCloseNews = findViewById(R.id.btnCloseNews);
        btnCloseNews.setOnClickListener(view -> finish());

        RecyclerView rvNews = findViewById(R.id.rvNews);
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(this::openDetails);
        rvNews.setAdapter(newsAdapter);

        ApiService apiService = ApiClient.create(new SessionManager(this));
        apiService.posts().enqueue(new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
                if (response.isSuccessful()) {
                    newsAdapter.submit(response.body());
                } else {
                    Toast.makeText(NewsActivity.this, R.string.news_load_failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {
                Toast.makeText(NewsActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openDetails(PostResponse post) {
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra("title", post.resolvedTitle());
        intent.putExtra("body", post.resolvedBody());
        startActivity(intent);
    }
}
