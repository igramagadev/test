package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class NewsDTO {
    @Data
    public static class NewsResponse {
        private Integer id;
        private String title;
        private String text;
        private String image_url;
        private String created_at;
        private Integer author_id;
    }

    @Data
    public static class NewsRequest {
        @NotBlank private String title;
        @NotBlank private String text;
        private String image_url;
    }
}
