package com.example.demo.controller;

import com.example.demo.dto.EventDTO;
import com.example.demo.dto.NewsDTO;
import com.example.demo.dto.ProfileDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/events")
    public EventDTO.EventResponse createEvent(@Valid @RequestBody EventDTO.EventRequest request) { return adminService.createEvent(request); }

    @PutMapping("/events/{eventId}")
    public EventDTO.EventResponse updateEvent(@PathVariable Integer eventId, @Valid @RequestBody EventDTO.EventRequest request) { return adminService.updateEvent(eventId, request); }

    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(@PathVariable Integer eventId) { adminService.deleteEvent(eventId); }

    @DeleteMapping("/events/{eventId}/participants/{userId}")
    public void removeParticipant(@PathVariable Integer eventId, @PathVariable Integer userId) { adminService.removeParticipant(eventId, userId); }

    @PutMapping("/users/{userId}")
    public UserDTO editUser(@PathVariable Integer userId, @Valid @RequestBody ProfileDTO.UpdateProfileRequest request) { return adminService.editUser(userId, request); }

    @PostMapping("/users/{userId}/block")
    public void blockUser(@PathVariable Integer userId) { adminService.blockUser(userId); }

    @PostMapping("/posts")
    public NewsDTO.NewsResponse createPost(@Valid @RequestBody NewsDTO.NewsRequest request) { return adminService.createPost(request); }

    @PutMapping("/posts/{postId}")
    public NewsDTO.NewsResponse updatePost(@PathVariable Integer postId, @Valid @RequestBody NewsDTO.NewsRequest request) { return adminService.updatePost(postId, request); }
}
