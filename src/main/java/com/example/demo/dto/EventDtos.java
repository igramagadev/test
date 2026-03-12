package com.example.demo.dto;

import jakarta.validation.constraints.*;
import java.time.Instant;

public class EventDtos {
    public record EventResponse(Long id, String title, String description, Double latitude, Double longitude, Instant eventDate, Integer maxParticipants, String status, long participants) {}
    public record ParticipantResponse(Long id, String username, String fullName) {}
    public record AdminEventRequest(@NotBlank String title, String description, @NotNull Double latitude, @NotNull Double longitude, @NotNull Instant eventDate, @NotNull @Min(1) Integer maxParticipants, String status) {}
}
