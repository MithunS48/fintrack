package com.fintrack.fintrack.controller;

import com.fintrack.fintrack.dto.login.LoginRequest;
import com.fintrack.fintrack.dto.login.LoginResponse;
import com.fintrack.fintrack.dto.login.RefreshTokenRequest;
import com.fintrack.fintrack.dto.user.UserRequest;
import com.fintrack.fintrack.dto.user.UserResponse;
import com.fintrack.fintrack.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserResponse registration(@Valid @RequestBody UserRequest request)
    {

        return authService.registration(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request)
    {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public LoginResponse refresh(@RequestBody RefreshTokenRequest token)
    {
        return authService.refreshToken(token.getToken());
    }
}
