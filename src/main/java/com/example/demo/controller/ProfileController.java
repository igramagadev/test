package com.example.demo.controller;

import com.example.demo.dto.ProfileDtos.*;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CurrentUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;

    @GetMapping("/api/profile")
    public ProfileResponse me(){
        User u = currentUserService.current();
        return new ProfileResponse(u.getId(),u.getEmail(),u.getUsername(),u.getFullName(),u.getPhone(),u.getBio(),u.getRole().name(),u.getStatus().name());
    }

    @PutMapping("/api/profile")
    public ProfileResponse update(@Valid @RequestBody UpdateProfileRequest req){
        User u = currentUserService.current();
        u.setFullName(req.fullName()); u.setPhone(req.phone()); u.setBio(req.bio());
        userRepository.save(u);
        return new ProfileResponse(u.getId(),u.getEmail(),u.getUsername(),u.getFullName(),u.getPhone(),u.getBio(),u.getRole().name(),u.getStatus().name());
    }

    @GetMapping("/api/users/{userId}")
    public PublicUserResponse user(@PathVariable Long userId){
        User u = userRepository.findById(userId).orElseThrow();
        return new PublicUserResponse(u.getId(),u.getUsername(),u.getFullName(),u.getBio());
    }
}
