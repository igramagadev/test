package com.example.demo.service;

import com.example.demo.dto.AuthDTO;

public interface AuthService {
    AuthDTO.TokenResponse register(AuthDTO.RegisterRequest request);
    AuthDTO.TokenResponse login(AuthDTO.LoginRequest request);
    String recover(AuthDTO.RecoverRequest request);
    void resetPassword(AuthDTO.ResetPasswordRequest request);
}
