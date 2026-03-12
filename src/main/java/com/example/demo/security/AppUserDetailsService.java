package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.repository.UserAuthorityRepository;
import com.example.demo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<SimpleGrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        userAuthorityRepository.findByUserId(user.getId()).forEach(a -> auth.add(new SimpleGrantedAuthority(a.getAuthority().getAuthority())));
        boolean blocked = auth.stream().anyMatch(a -> a.getAuthority().equals("BLOCKED"));
        return new AppUserPrincipal(user.getId(), user.getEmail(), user.getPassword(), blocked, auth);
    }
}
