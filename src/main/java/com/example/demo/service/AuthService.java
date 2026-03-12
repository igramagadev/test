package com.example.demo.service;

import com.example.demo.dto.AuthDtos.*;
import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.PasswordResetTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final PasswordResetTokenRepository tokenRepository;

    public AuthResponse register(RegisterRequest req){
        if (userRepository.existsByEmail(req.email())) throw new BadRequestException("Email already exists");
        if (userRepository.existsByUsername(req.username())) throw new BadRequestException("Username already exists");
        User user = new User();
        user.setEmail(req.email()); user.setUsername(req.username()); user.setPasswordHash(encoder.encode(req.password()));
        user.setFullName(req.fullName()); user.setPhone(req.phone()); user.setRole(UserRole.USER);
        userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(user.getId(), user.getRole().name()));
    }

    public AuthResponse login(LoginRequest req){
        User user = userRepository.findByEmail(req.login()).or(() -> userRepository.findByUsername(req.login()))
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (user.getStatus() == UserStatus.BLOCKED) throw new BadRequestException("User is blocked");
        authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getId().toString(), req.password()));
        return new AuthResponse(jwtService.generateToken(user.getId(), user.getRole().name()));
    }

    public String recover(RecoverRequest req){
        User user = userRepository.findByEmail(req.account()).or(() -> userRepository.findByUsername(req.account()))
                .orElseThrow(() -> new NotFoundException("User not found"));
        PasswordResetToken t = new PasswordResetToken();
        t.setUser(user); t.setToken(UUID.randomUUID().toString()); t.setExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES));
        tokenRepository.save(t);
        return "Recovery token generated: " + t.getToken();
    }

    public void resetPassword(ResetPasswordRequest req){
        PasswordResetToken token = tokenRepository.findByToken(req.token()).orElseThrow(() -> new NotFoundException("Token not found"));
        if (token.isUsed() || token.getExpiresAt().isBefore(Instant.now())) throw new BadRequestException("Token expired or used");
        User user = token.getUser();
        user.setPasswordHash(encoder.encode(req.newPassword()));
        userRepository.save(user);
        token.setUsed(true);
        tokenRepository.save(token);
    }
}
