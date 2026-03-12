package com.example.demo.controller;

import com.example.demo.dto.EventDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Events")
public class EventController {
    private final EventService eventService;

    @GetMapping
    public List<EventDTO.EventResponse> getEvents(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size,
                                                  @RequestParam(required = false) Double lat,
                                                  @RequestParam(required = false) Double lng,
                                                  @RequestParam(required = false) Double radius) {
        return eventService.getEvents(page, size, lat, lng, radius);
    }

    @GetMapping("/{eventId}/participants")
    public List<UserDTO> getParticipants(@PathVariable Integer eventId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        return eventService.getParticipants(eventId, page, size);
    }

    @PostMapping("/{eventId}/join")
    public void joinEvent(@PathVariable Integer eventId) { eventService.joinEvent(eventId); }
}
