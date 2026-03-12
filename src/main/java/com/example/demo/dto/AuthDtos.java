package com.example.demo.dto;

import jakarta.validation.constraints.*;

public class AuthDtos {
    public record RegisterRequest(@Email String email, @NotBlank String username, @NotBlank @Size(min=8) String password, @NotBlank String fullName, String phone) {}
    public record LoginRequest(@NotBlank String login, @NotBlank String password) {}
    public record RecoverRequest(@NotBlank String account) {}
    public record ResetPasswordRequest(@NotBlank String token, @NotBlank @Size(min=8) String newPassword) {}
    public record AuthResponse(String accessToken) {}
}
