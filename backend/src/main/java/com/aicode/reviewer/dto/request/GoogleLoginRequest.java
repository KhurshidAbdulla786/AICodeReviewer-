package com.aicode.reviewer.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request DTO for Google OAuth login.
 * The frontend sends the Google ID token obtained from the Google Sign-In flow.
 *
 * @author AI Code Reviewer Team
 */
@Data
public class GoogleLoginRequest {

    @NotBlank(message = "Google ID token is required")
    private String idToken;
}
