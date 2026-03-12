package com.example.demo.controller;

import com.example.demo.dto.ProfileDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CurrentUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Profile")
public class ProfileController {
    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;

    @GetMapping("/api/profile")
    public UserDTO myProfile() {
        return toDto(currentUserService.getCurrentUser());
    }

    @PutMapping("/api/profile")
    public UserDTO updateProfile(@Valid @RequestBody ProfileDTO.UpdateProfileRequest request) {
        User user = currentUserService.getCurrentUser();
        user.setName(request.getName());
        user.setAvatarUrl(request.getAvatar_url());
        userRepository.save(user);
        return toDto(user);
    }

    @GetMapping("/api/users/{userId}")
    public UserDTO getPublicUser(@PathVariable Integer userId) {
        return toDto(userRepository.findById(userId).orElseThrow());
    }

    private UserDTO toDto(User user) {
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
