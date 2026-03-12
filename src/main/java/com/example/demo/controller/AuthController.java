package com.example.demo.controller;

import com.example.demo.dto.AuthDTO;
import com.example.demo.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthDTO.TokenResponse register(@Valid @RequestBody AuthDTO.RegisterRequest request) { return authService.register(request); }

    @PostMapping("/login")
    public AuthDTO.TokenResponse login(@Valid @RequestBody AuthDTO.LoginRequest request) { return authService.login(request); }

    @PostMapping("/recover")
    public String recover(@Valid @RequestBody AuthDTO.RecoverRequest request) { return authService.recover(request); }

    @PostMapping("/reset-password")
    public void resetPassword(@Valid @RequestBody AuthDTO.ResetPasswordRequest request) { authService.resetPassword(request); }
}
