package com.example.test.data.network;

import com.example.test.data.network.dto.AuthRequest;
import com.example.test.data.network.dto.EventResponse;
import com.example.test.data.network.dto.PostResponse;
import com.example.test.data.network.dto.ProfileResponse;
import com.example.test.data.network.dto.RegisterRequest;
import com.example.test.data.network.dto.TokenResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/auth/login")
    Call<TokenResponse> login(@Body AuthRequest request);

    @POST("api/auth/register")
    Call<TokenResponse> register(@Body RegisterRequest request);

    @POST("api/auth/recover")
    Call<Void> recover(@Body Map<String, String> body);

    @GET("api/profile")
    Call<ProfileResponse> profile();

    @GET("api/events")
    Call<List<EventResponse>> events();

    @GET("api/posts")
    Call<List<PostResponse>> posts();

    @POST("api/events/{eventId}/join")
    Call<Void> joinEvent(@retrofit2.http.Path("eventId") long eventId);
}
