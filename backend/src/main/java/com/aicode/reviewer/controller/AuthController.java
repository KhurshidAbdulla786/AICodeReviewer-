package com.aicode.reviewer.controller;

import com.aicode.reviewer.dto.request.LoginRequest;
import com.aicode.reviewer.dto.request.RefreshTokenRequest;
import com.aicode.reviewer.dto.request.RegisterRequest;
import com.aicode.reviewer.dto.response.ApiResponse;
import com.aicode.reviewer.dto.response.JwtAuthResponse;
import com.aicode.reviewer.security.CustomUserDetails;
import com.aicode.reviewer.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication operations.
 *
 * @author AI Code Reviewer Team
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<JwtAuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        JwtAuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Registration successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtAuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        JwtAuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<JwtAuthResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        JwtAuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Token refreshed"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
        authService.logout(userDetails.getId());
        return ResponseEntity.ok(ApiResponse.<Void>success(null, "Logged out successfully"));
    }
}