package com.example.test.data.network.dto;

import com.google.gson.annotations.SerializedName;

public class PostResponse {
    public Long id;

    @SerializedName("title")
    public String title;

    @SerializedName("name")
    public String name;

    public String description;
    public String content;

    public String resolvedTitle() {
        return title != null ? title : name;
    }

    public String resolvedBody() {
        return content != null ? content : description;
    }
}
