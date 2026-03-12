package com.example.demo.repository;

import com.example.demo.entity.UserAuthority;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Integer> {
    List<UserAuthority> findByUserId(Integer userId);
    boolean existsByUserIdAndAuthorityAuthority(Integer userId, String authority);
}
