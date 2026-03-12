package com.example.demo.controller;

import com.example.demo.dto.EventDtos.*;
import com.example.demo.dto.ProfileDtos.ProfileResponse;
import com.example.demo.dto.ProfileDtos.UpdateProfileRequest;
import com.example.demo.entity.*;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.*;
import jakarta.validation.Valid;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final EventRepository eventRepository;
    private final EventParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @PostMapping("/events")
    public EventResponse createEvent(@Valid @RequestBody AdminEventRequest req){
        Event e = new Event(); apply(e, req); eventRepository.save(e);
        return new EventResponse(e.getId(),e.getTitle(),e.getDescription(),e.getLatitude(),e.getLongitude(),e.getEventDate(),e.getMaxParticipants(),e.getStatus().name(),0);
    }

    @PutMapping("/events/{eventId}")
    public EventResponse updateEvent(@PathVariable Long eventId,@Valid @RequestBody AdminEventRequest req){
        Event e = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found")); apply(e, req); eventRepository.save(e);
        return new EventResponse(e.getId(),e.getTitle(),e.getDescription(),e.getLatitude(),e.getLongitude(),e.getEventDate(),e.getMaxParticipants(),e.getStatus().name(),participantRepository.countByEventId(e.getId()));
    }

    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(@PathVariable Long eventId){ Event e = eventRepository.findById(eventId).orElseThrow(); e.setDeleted(true); eventRepository.save(e); }

    @DeleteMapping("/events/{eventId}/participants/{userId}")
    public void removeParticipant(@PathVariable Long eventId,@PathVariable Long userId){
        EventParticipant p = participantRepository.findByEventIdAndUserId(eventId,userId).orElseThrow(() -> new NotFoundException("Participant not found"));
        participantRepository.delete(p);
    }

    @PutMapping("/users/{userId}")
    public ProfileResponse updateUser(@PathVariable Long userId,@Valid @RequestBody UpdateProfileRequest req){
        User u = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        u.setFullName(req.fullName()); u.setPhone(req.phone()); u.setBio(req.bio()); userRepository.save(u);
        return new ProfileResponse(u.getId(),u.getEmail(),u.getUsername(),u.getFullName(),u.getPhone(),u.getBio(),u.getRole().name(),u.getStatus().name());
    }

    @PostMapping("/users/{userId}/block")
    public void block(@PathVariable Long userId){
        User u = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        u.setStatus(UserStatus.BLOCKED); u.setUpdatedAt(Instant.now()); userRepository.save(u);
    }

    private void apply(Event e, AdminEventRequest req){
        e.setTitle(req.title()); e.setDescription(req.description()); e.setLatitude(req.latitude()); e.setLongitude(req.longitude());
        e.setEventDate(req.eventDate()); e.setMaxParticipants(req.maxParticipants());
        e.setStatus(req.status()==null?EventStatus.OPEN:EventStatus.valueOf(req.status()));
    }
}
