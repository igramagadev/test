package com.example.demo.controller;

import com.example.demo.dto.ProfileDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Profile")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/api/profile")
    public UserDTO myProfile() { return profileService.getMyProfile(); }

    @PutMapping("/api/profile")
    public UserDTO updateProfile(@Valid @RequestBody ProfileDTO.UpdateProfileRequest request) { return profileService.updateProfile(request); }

    @GetMapping("/api/users/{userId}")
    public UserDTO getPublicUser(@PathVariable Integer userId) { return profileService.getPublicUser(userId); }
}
