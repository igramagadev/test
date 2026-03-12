package com.example.test.data.network.dto;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    @SerializedName("token")
    public String token;

    @SerializedName("accessToken")
    public String accessToken;

    public String resolvedToken() {
        return token != null ? token : accessToken;
    }
}
