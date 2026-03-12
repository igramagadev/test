package com.example.test.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.data.network.dto.PostResponse;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsVH> {

    public interface OnNewsClickListener {
        void onClick(PostResponse post);
    }

    private final List<PostResponse> data = new ArrayList<>();
    private final OnNewsClickListener listener;

    public NewsAdapter(OnNewsClickListener listener) {
        this.listener = listener;
    }

    public void submit(List<PostResponse> posts) {
        data.clear();
        if (posts != null) {
            data.addAll(posts);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsVH holder, int position) {
        PostResponse post = data.get(position);
        String title = post.resolvedTitle();
        holder.tvTitle.setText(title == null || title.trim().isEmpty() ? "Новость" : title);
        holder.itemView.setOnClickListener(v -> listener.onClick(post));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class NewsVH extends RecyclerView.ViewHolder {
        TextView tvTitle;

        NewsVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNewsTitle);
        }
    }
}
