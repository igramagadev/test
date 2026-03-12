package com.example.test.data.network.dto;

import com.google.gson.annotations.SerializedName;

public class EventResponse {
    public Long id;

    @SerializedName("title")
    public String title;

    @SerializedName("name")
    public String name;

    public String description;
    public String address;
    public String date;

    @SerializedName("eventDate")
    public String eventDate;

    public Integer points;

    public String resolvedTitle() {
        return title != null ? title : name;
    }

    public String resolvedDate() {
        return date != null ? date : eventDate;
    }
}
