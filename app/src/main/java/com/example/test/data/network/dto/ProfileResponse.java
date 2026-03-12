package com.example.test.data.network.dto;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    public Long id;

    @SerializedName("fullName")
    public String fullName;

    @SerializedName("name")
    public String name;

    public String email;
    public String school;
    public String role;
}
