package com.example.demo.repository;

import com.example.demo.entity.Registration;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    long countByEventId(Integer eventId);
    boolean existsByEventIdAndUserId(Integer eventId, Integer userId);
    List<Registration> findByEventId(Integer eventId);
    Page<Registration> findByEventId(Integer eventId, Pageable pageable);
    Optional<Registration> findByEventIdAndUserId(Integer eventId, Integer userId);
}
