package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

public class EventDTO {
    @Data
    public static class EventResponse {
        private Integer id;
        private String title;
        private String description;
        private LocalDateTime start_date;
        private Integer max_participants;
        private Integer organizer_id;
        private Long participants_count;
        private String address;
        private Double latitude;
        private Double longitude;
    }

    @Data
    public static class EventRequest {
        @NotBlank private String title;
        @NotBlank private String description;
        @NotNull private LocalDateTime start_date;
        @NotNull @Min(1) private Integer max_participants;
        @NotBlank private String address;
        @NotNull private Double latitude;
        @NotNull private Double longitude;
    }
}
