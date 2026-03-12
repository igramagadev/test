package com.example.demo.repository;

import com.example.demo.entity.Authority;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> { Optional<Authority> findByAuthority(String authority); }
