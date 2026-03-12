package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class ProfileDtos {
    public record ProfileResponse(Long id, String email, String username, String fullName, String phone, String bio, String role, String status) {}
    public record UpdateProfileRequest(@NotBlank String fullName, String phone, String bio) {}
    public record PublicUserResponse(Long id, String username, String fullName, String bio) {}
}
