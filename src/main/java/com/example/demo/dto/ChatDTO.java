package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class ChatDTO {
    @Data
    public static class MessageRequest {
        @NotBlank private String text;
        private Integer chatId = 1;
    }

    @Data
    public static class MessageResponse {
        private Integer id;
        private String text;
        private String sent_at;
        private Integer user_id;
        private Integer chat_id;
    }
}
