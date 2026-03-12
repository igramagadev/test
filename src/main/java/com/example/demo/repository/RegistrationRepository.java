package com.example.demo.repository;

import com.example.demo.entity.Registration;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    long countByEventId(Integer eventId);
    boolean existsByEventIdAndUserId(Integer eventId, Integer userId);
    List<Registration> findByEventId(Integer eventId);
    Optional<Registration> findByEventIdAndUserId(Integer eventId, Integer userId);
}
