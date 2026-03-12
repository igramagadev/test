package com.example.demo.service.impl;

import com.example.demo.dto.AuthDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import com.example.demo.service.AuthService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthDTO.TokenResponse register(AuthDTO.RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) throw new BadRequestException("Email already exists");
        Role userRole = roleRepository.findByName("USER").orElseThrow();
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(userRole);
        user.setPoints(0);
        userRepository.save(user);
        return new AuthDTO.TokenResponse(jwtService.generateToken(user.getEmail(), user.getRole().getName()));
    }

    @Override
    public AuthDTO.TokenResponse login(AuthDTO.LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if ("BLOCKED".equalsIgnoreCase(user.getRole().getName())) throw new BadRequestException("User is blocked");
        return new AuthDTO.TokenResponse(jwtService.generateToken(user.getEmail(), user.getRole().getName()));
    }

    @Override
    public String recover(AuthDTO.RecoverRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadRequestException("User not found"));
        return jwtService.generateToken(user.getEmail(), "RESET_" + Instant.now().toEpochMilli());
    }

    @Override
    public void resetPassword(AuthDTO.ResetPasswordRequest request) {
        String email = jwtService.extractSubject(request.getToken());
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("Invalid token"));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
