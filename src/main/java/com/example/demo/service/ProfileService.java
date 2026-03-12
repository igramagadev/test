package com.example.demo.service;

import com.example.demo.dto.ProfileDTO;
import com.example.demo.dto.UserDTO;

public interface ProfileService {
    UserDTO getMyProfile();
    UserDTO updateProfile(ProfileDTO.UpdateProfileRequest request);
    UserDTO getPublicUser(Integer userId);
}
