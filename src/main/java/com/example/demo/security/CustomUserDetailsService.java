package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.entity.UserStatus;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if (username.chars().allMatch(Character::isDigit)) {
            user = userRepository.findById(Long.valueOf(username)).orElseThrow(() -> new NotFoundException("User not found"));
        } else {
            user = userRepository.findByEmail(username)
                    .or(() -> userRepository.findByUsername(username))
                    .orElseThrow(() -> new NotFoundException("User not found"));
        }
        boolean active = user.getStatus() == UserStatus.ACTIVE;
        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPasswordHash(), active, true, true, active,
                java.util.List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
    }
}
