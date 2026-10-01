package com.aicode.reviewer.service;

import com.aicode.reviewer.dto.request.LoginRequest;
import com.aicode.reviewer.dto.request.RefreshTokenRequest;
import com.aicode.reviewer.dto.request.RegisterRequest;
import com.aicode.reviewer.dto.response.JwtAuthResponse;

/**
 * Service interface for authentication operations.
 *
 * @author AI Code Reviewer Team
 */
public interface AuthService {

    /**
     * Registers a new user account.
     *
     * @param request registration details
     * @return JWT authentication response
     */
    JwtAuthResponse register(RegisterRequest request);

    /**
     * Authenticates a user and returns JWT tokens.
     *
     * @param request login credentials
     * @return JWT authentication response
     */
    JwtAuthResponse login(LoginRequest request);

    /**
     * Refreshes an expired access token using a refresh token.
     *
     * @param request refresh token
     * @return new JWT authentication response
     */
    JwtAuthResponse refreshToken(RefreshTokenRequest request);

    /**
     * Logs out a user by revoking all their refresh tokens.
     *
     * @param userId the user ID
     */
    void logout(Long userId);
}