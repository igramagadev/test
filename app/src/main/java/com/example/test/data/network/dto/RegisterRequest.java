package com.example.test.data.network.dto;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("fullName")
    public String fullName;
    public String email;
    public String password;

    public RegisterRequest(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }
}
