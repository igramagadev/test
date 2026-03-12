package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class ProfileDTO {
    @Data
    public static class UpdateProfileRequest {
        @NotBlank private String name;
        private String avatar_url;
    }
}
