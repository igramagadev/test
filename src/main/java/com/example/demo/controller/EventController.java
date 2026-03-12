package com.example.demo.controller;

import com.example.demo.dto.EventDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.*;
import com.example.demo.service.CurrentUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Events")
public class EventController {
    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;
    private final LocationRepository locationRepository;
    private final CurrentUserService currentUserService;

    @GetMapping
    public List<EventDTO.EventResponse> getEvents(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size,
                                                  @RequestParam(required = false) Double lat,
                                                  @RequestParam(required = false) Double lng,
                                                  @RequestParam(required = false) Double radius) {
        return eventRepository.findAll(PageRequest.of(page, size)).stream()
                .map(this::toEventDto)
                .filter(e -> lat == null || lng == null || radius == null || Math.hypot(e.getLatitude() - lat, e.getLongitude() - lng) <= radius)
                .toList();
    }

    @GetMapping("/{eventId}/participants")
    public List<UserDTO> getParticipants(@PathVariable Integer eventId) {
        return registrationRepository.findByEventId(eventId).stream().map(Registration::getUser).map(this::toUserDto).toList();
    }

    @PostMapping("/{eventId}/join")
    public void joinEvent(@PathVariable Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        User user = currentUserService.getCurrentUser();
        if (registrationRepository.existsByEventIdAndUserId(eventId, user.getId())) {
            throw new BadRequestException("Already joined");
        }
        if (registrationRepository.countByEventId(eventId) >= event.getMaxParticipants()) {
            throw new BadRequestException("No free places");
        }
        Registration registration = new Registration();
        registration.setEvent(event);
        registration.setUser(user);
        registrationRepository.save(registration);
    }

    private EventDTO.EventResponse toEventDto(Event event) {
        EventDTO.EventResponse dto = new EventDTO.EventResponse();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setStart_date(event.getStartDate());
        dto.setMax_participants(event.getMaxParticipants());
        dto.setOrganizer_id(event.getOrganizer().getId());
        dto.setParticipants_count(registrationRepository.countByEventId(event.getId()));
        Location loc = locationRepository.findByEventId(event.getId()).orElse(null);
        if (loc != null) { dto.setAddress(loc.getAddress()); dto.setLatitude(loc.getLatitude()); dto.setLongitude(loc.getLongitude()); }
        return dto;
    }

    private UserDTO toUserDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole_id(user.getRole().getId());
        dto.setPoints(user.getPoints());
        dto.setAvatar_url(user.getAvatarUrl());
        return dto;
    }
}
