package com.example.demo.controller;

import com.example.demo.dto.EventDtos.*;
import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.CurrentUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventRepository eventRepository;
    private final EventParticipantRepository participantRepository;
    private final CurrentUserService currentUserService;

    @GetMapping
    public List<EventResponse> list(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "20") int size,
                                    @RequestParam(required = false) Double lat,@RequestParam(required = false) Double lng,@RequestParam(required = false) Double radius){
        return eventRepository.findByDeletedFalse(PageRequest.of(page,size)).stream()
                .filter(e -> lat==null || lng==null || radius==null || Math.hypot(e.getLatitude()-lat,e.getLongitude()-lng)<=radius)
                .map(e -> new EventResponse(e.getId(),e.getTitle(),e.getDescription(),e.getLatitude(),e.getLongitude(),e.getEventDate(),e.getMaxParticipants(),e.getStatus().name(),participantRepository.countByEventId(e.getId())))
                .toList();
    }

    @GetMapping("/{eventId}/participants")
    public List<ParticipantResponse> participants(@PathVariable Long eventId){
        return participantRepository.findByEventId(eventId).stream().map(p -> new ParticipantResponse(p.getUser().getId(), p.getUser().getUsername(), p.getUser().getFullName())).toList();
    }

    @PostMapping("/{eventId}/join")
    public void join(@PathVariable Long eventId){
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found"));
        if (event.getStatus()!=EventStatus.OPEN) throw new BadRequestException("Event is closed");
        User user = currentUserService.current();
        if (participantRepository.existsByEventAndUser(event,user)) throw new BadRequestException("Already joined");
        if (participantRepository.countByEventId(eventId)>=event.getMaxParticipants()) throw new BadRequestException("No free places");
        EventParticipant p = new EventParticipant(); p.setEvent(event); p.setUser(user); participantRepository.save(p);
    }
}
