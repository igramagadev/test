package com.example.demo.repository;

import com.example.demo.entity.Location;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> { Optional<Location> findByEventId(Integer eventId); }
