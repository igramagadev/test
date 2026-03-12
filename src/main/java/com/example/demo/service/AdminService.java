package com.example.demo.service;

import com.example.demo.dto.EventDTO;
import com.example.demo.dto.NewsDTO;
import com.example.demo.dto.ProfileDTO;
import com.example.demo.dto.UserDTO;

public interface AdminService {
    EventDTO.EventResponse createEvent(EventDTO.EventRequest request);
    EventDTO.EventResponse updateEvent(Integer eventId, EventDTO.EventRequest request);
    void deleteEvent(Integer eventId);
    void removeParticipant(Integer eventId, Integer userId);
    UserDTO editUser(Integer userId, ProfileDTO.UpdateProfileRequest request);
    void blockUser(Integer userId);
    NewsDTO.NewsResponse createPost(NewsDTO.NewsRequest request);
    NewsDTO.NewsResponse updatePost(Integer postId, NewsDTO.NewsRequest request);
}
