package com.example.demo.service.impl;

import com.example.demo.dto.EventDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Event;
import com.example.demo.entity.Location;
import com.example.demo.entity.Registration;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.RegistrationRepository;
import com.example.demo.service.CurrentUserService;
import com.example.demo.service.EventService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;
    private final LocationRepository locationRepository;
    private final CurrentUserService currentUserService;

    @Override
    public List<EventDTO.EventResponse> getEvents(int page, int size, Double lat, Double lng, Double radius) {
        return eventRepository.findAll(PageRequest.of(page, size)).stream().map(this::toEventDto)
                .filter(e -> lat == null || lng == null || radius == null || Math.hypot(e.getLatitude() - lat, e.getLongitude() - lng) <= radius)
                .toList();
    }

    @Override
    public List<UserDTO> getParticipants(Integer eventId, int page, int size) {
        return registrationRepository.findByEventId(eventId, PageRequest.of(page, size)).stream().map(Registration::getUser).map(this::toUserDto).toList();
    }

    @Override
    public void joinEvent(Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        User user = currentUserService.getCurrentUser();
        if (registrationRepository.existsByEventIdAndUserId(eventId, user.getId())) throw new BadRequestException("Already joined");
        if (registrationRepository.countByEventId(eventId) >= event.getMaxParticipants()) throw new BadRequestException("No free places");
        Registration registration = new Registration();
        registration.setEvent(event);
        registration.setUser(user);
        registrationRepository.save(registration);
    }

    private EventDTO.EventResponse toEventDto(Event event) {
        EventDTO.EventResponse dto = new EventDTO.EventResponse();
        dto.setId(event.getId()); dto.setTitle(event.getTitle()); dto.setDescription(event.getDescription()); dto.setStart_date(event.getStartDate());
        dto.setMax_participants(event.getMaxParticipants()); dto.setOrganizer_id(event.getOrganizer().getId()); dto.setParticipants_count(registrationRepository.countByEventId(event.getId()));
        Location loc = locationRepository.findByEventId(event.getId()).orElse(null);
        if (loc != null) { dto.setAddress(loc.getAddress()); dto.setLatitude(loc.getLatitude()); dto.setLongitude(loc.getLongitude()); }
        return dto;
    }

    private UserDTO toUserDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId()); dto.setName(user.getName()); dto.setEmail(user.getEmail()); dto.setRole_id(user.getRole().getId()); dto.setPoints(user.getPoints()); dto.setAvatar_url(user.getAvatarUrl());
        return dto;
    }
}
