package com.example.demo.service.impl;

import com.example.demo.dto.ProfileDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CurrentUserService;
import com.example.demo.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;

    @Override
    public UserDTO getMyProfile() { return toDto(currentUserService.getCurrentUser()); }

    @Override
    public UserDTO updateProfile(ProfileDTO.UpdateProfileRequest request) {
        User user = currentUserService.getCurrentUser();
        user.setName(request.getName());
        user.setAvatarUrl(request.getAvatar_url());
        userRepository.save(user);
        return toDto(user);
    }

    @Override
    public UserDTO getPublicUser(Integer userId) { return toDto(userRepository.findById(userId).orElseThrow()); }

    private UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId()); dto.setName(user.getName()); dto.setEmail(user.getEmail()); dto.setRole_id(user.getRole().getId()); dto.setPoints(user.getPoints()); dto.setAvatar_url(user.getAvatarUrl());
        return dto;
    }
}
