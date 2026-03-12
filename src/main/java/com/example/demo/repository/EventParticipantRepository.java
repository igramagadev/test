package com.example.demo.repository;
import com.example.demo.entity.*;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {
    long countByEventId(Long eventId);
    List<EventParticipant> findByEventId(Long eventId);
    boolean existsByEventAndUser(Event event, User user);
    Optional<EventParticipant> findByEventIdAndUserId(Long eventId, Long userId);
}
