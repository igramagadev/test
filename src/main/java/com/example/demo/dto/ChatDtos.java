package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

public class ChatDtos {
    public record ChatMessageResponse(Long id, Long authorId, String authorName, String text, Instant createdAt, Instant updatedAt) {}
    public record CreateMessageRequest(@NotBlank String text) {}
    public record UpdateMessageRequest(@NotBlank String text) {}
}
