package com.example.demo.controller;

import com.example.demo.dto.AuthDtos.*;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest req){ return authService.register(req); }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req){ return authService.login(req); }

    @PostMapping("/recover")
    public ResponseEntity<String> recover(@Valid @RequestBody RecoverRequest req){ return ResponseEntity.ok(authService.recover(req)); }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> reset(@Valid @RequestBody ResetPasswordRequest req){ authService.resetPassword(req); return ResponseEntity.noContent().build(); }
}
