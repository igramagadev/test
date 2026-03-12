package com.example.demo.controller;

import com.example.demo.dto.AuthDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public AuthDTO.TokenResponse register(@Valid @RequestBody AuthDTO.RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
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

    @PostMapping("/login")
    public AuthDTO.TokenResponse login(@Valid @RequestBody AuthDTO.LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return new AuthDTO.TokenResponse(jwtService.generateToken(user.getEmail(), user.getRole().getName()));
    }

    @PostMapping("/recover")
    public String recover(@Valid @RequestBody AuthDTO.RecoverRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadRequestException("User not found"));
        return jwtService.generateToken(user.getEmail(), "RESET_" + Instant.now().toEpochMilli());
    }

    @PostMapping("/reset-password")
    public void resetPassword(@Valid @RequestBody AuthDTO.ResetPasswordRequest request) {
        String email = jwtService.extractSubject(request.getToken());
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("Invalid token"));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
