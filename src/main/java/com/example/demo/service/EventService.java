package com.example.demo.service;

import com.example.demo.dto.EventDTO;
import com.example.demo.dto.UserDTO;
import java.util.List;

public interface EventService {
    List<EventDTO.EventResponse> getEvents(int page, int size, Double lat, Double lng, Double radius);
    List<UserDTO> getParticipants(Integer eventId, int page, int size);
    void joinEvent(Integer eventId);
}
