package com.example.demo.service.impl;

import com.example.demo.dto.EventDTO;
import com.example.demo.dto.NewsDTO;
import com.example.demo.dto.ProfileDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.AdminService;
import com.example.demo.service.CurrentUserService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final RoleRepository roleRepository;
    private final CurrentUserService currentUserService;

    @Override
    public EventDTO.EventResponse createEvent(EventDTO.EventRequest request) {
        Event event = new Event();
        event.setTitle(request.getTitle()); event.setDescription(request.getDescription()); event.setStartDate(request.getStart_date()); event.setMaxParticipants(request.getMax_participants()); event.setOrganizer(currentUserService.getCurrentUser());
        eventRepository.save(event);
        Location location = new Location();
        location.setEvent(event); location.setAddress(request.getAddress()); location.setLatitude(request.getLatitude()); location.setLongitude(request.getLongitude());
        locationRepository.save(location);
        return mapEvent(event, location);
    }

    @Override
    public EventDTO.EventResponse updateEvent(Integer eventId, EventDTO.EventRequest request) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        Location location = locationRepository.findByEventId(eventId).orElseThrow();
        event.setTitle(request.getTitle()); event.setDescription(request.getDescription()); event.setStartDate(request.getStart_date()); event.setMaxParticipants(request.getMax_participants());
        location.setAddress(request.getAddress()); location.setLatitude(request.getLatitude()); location.setLongitude(request.getLongitude());
        eventRepository.save(event); locationRepository.save(location);
        return mapEvent(event, location);
    }

    @Override
    public void deleteEvent(Integer eventId) {
        locationRepository.findByEventId(eventId).ifPresent(locationRepository::delete);
        eventRepository.deleteById(eventId);
    }

    @Override
    public void removeParticipant(Integer eventId, Integer userId) {
        Registration reg = registrationRepository.findByEventIdAndUserId(eventId, userId).orElseThrow();
        registrationRepository.delete(reg);
    }

    @Override
    public UserDTO editUser(Integer userId, ProfileDTO.UpdateProfileRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setName(request.getName()); user.setAvatarUrl(request.getAvatar_url());
        userRepository.save(user);
        UserDTO dto = new UserDTO();
        dto.setId(user.getId()); dto.setName(user.getName()); dto.setEmail(user.getEmail()); dto.setRole_id(user.getRole().getId()); dto.setPoints(user.getPoints()); dto.setAvatar_url(user.getAvatarUrl());
        return dto;
    }

    @Override
    public void blockUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Role blockedRole = roleRepository.findByName("BLOCKED").orElseThrow();
        user.setRole(blockedRole);
        userRepository.save(user);
    }

    @Override
    public NewsDTO.NewsResponse createPost(NewsDTO.NewsRequest request) {
        News news = new News();
        news.setTitle(request.getTitle()); news.setText(request.getText()); news.setImageUrl(request.getImage_url()); news.setCreatedAt(LocalDateTime.now()); news.setAuthor(currentUserService.getCurrentUser());
        return toNewsDto(newsRepository.save(news));
    }

    @Override
    public NewsDTO.NewsResponse updatePost(Integer postId, NewsDTO.NewsRequest request) {
        News news = newsRepository.findById(postId).orElseThrow();
        news.setTitle(request.getTitle()); news.setText(request.getText()); news.setImageUrl(request.getImage_url());
        return toNewsDto(newsRepository.save(news));
    }

    private EventDTO.EventResponse mapEvent(Event event, Location location) {
        EventDTO.EventResponse dto = new EventDTO.EventResponse();
        dto.setId(event.getId()); dto.setTitle(event.getTitle()); dto.setDescription(event.getDescription()); dto.setStart_date(event.getStartDate()); dto.setMax_participants(event.getMaxParticipants()); dto.setOrganizer_id(event.getOrganizer().getId()); dto.setParticipants_count(registrationRepository.countByEventId(event.getId()));
        dto.setAddress(location.getAddress()); dto.setLatitude(location.getLatitude()); dto.setLongitude(location.getLongitude());
        return dto;
    }

    private NewsDTO.NewsResponse toNewsDto(News n) {
        NewsDTO.NewsResponse dto = new NewsDTO.NewsResponse();
        dto.setId(n.getId()); dto.setTitle(n.getTitle()); dto.setText(n.getText()); dto.setImage_url(n.getImageUrl()); dto.setCreated_at(n.getCreatedAt().toString()); dto.setAuthor_id(n.getAuthor().getId());
        return dto;
    }
}
