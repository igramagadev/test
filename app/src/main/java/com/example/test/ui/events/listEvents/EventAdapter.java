package com.example.test.ui.events.listEvents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.data.network.dto.EventResponse;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventVH> {

    public interface OnEventClickListener {
        void onClick(EventResponse event);
    }

    private final List<EventResponse> data = new ArrayList<>();
    private final OnEventClickListener listener;

    public EventAdapter(OnEventClickListener listener) {
        this.listener = listener;
    }

    public void submit(List<EventResponse> events) {
        data.clear();
        if (events != null) {
            data.addAll(events);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventVH holder, int position) {
        EventResponse event = data.get(position);
        holder.tvTitle.setText(valueOrDefault(event.resolvedTitle(), "Событие"));
        holder.tvAddress.setText(valueOrDefault(event.address, "Адрес не указан"));
        holder.tvDate.setText(valueOrDefault(event.resolvedDate(), "Дата не указана"));
        holder.tvPoints.setText(String.valueOf(event.points == null ? 0 : event.points));
        holder.itemView.setOnClickListener(v -> listener.onClick(event));
        holder.tvDetails.setOnClickListener(v -> listener.onClick(event));
        holder.ivEdit.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private String valueOrDefault(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value;
    }

    static class EventVH extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDetails;
        TextView tvAddress;
        TextView tvDate;
        TextView tvPoints;
        View ivEdit;

        public EventVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvEventTitle);
            tvDetails = itemView.findViewById(R.id.tvEventDetails);
            tvAddress = itemView.findViewById(R.id.tvEventAddress);
            tvDate = itemView.findViewById(R.id.tvEventDate);
            tvPoints = itemView.findViewById(R.id.tvEventPoints);
            ivEdit = itemView.findViewById(R.id.ivEditEvent);
        }
    }
}
