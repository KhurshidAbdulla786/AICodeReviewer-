package com.aicode.reviewer.service;

import com.aicode.reviewer.exception.BadRequestException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service for verifying Google OAuth ID tokens and extracting user info.
 *
 * @author AI Code Reviewer Team
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuthService {

    private static final String GOOGLE_TOKEN_INFO_URL =
            "https://oauth2.googleapis.com/tokeninfo?id_token=";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Verifies a Google ID token and returns the payload as a JsonNode.
     *
     * @param idToken the Google ID token sent from the frontend
     * @return JsonNode containing user info (email, name, sub, picture, etc.)
     */
    public JsonNode verifyGoogleToken(String idToken) {
        try {
            String url = GOOGLE_TOKEN_INFO_URL + idToken;
            String response = restTemplate.getForObject(url, String.class);
            JsonNode tokenInfo = objectMapper.readTree(response);

            if (tokenInfo.has("error_description")) {
                log.warn("Invalid Google token: {}", tokenInfo.get("error_description").asText());
                throw new BadRequestException("Invalid Google token: " +
                        tokenInfo.get("error_description").asText());
            }

            log.debug("Google token verified for email: {}",
                    tokenInfo.has("email") ? tokenInfo.get("email").asText() : "unknown");
            return tokenInfo;

        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to verify Google token: {}", e.getMessage());
            throw new BadRequestException("Failed to verify Google token. Please try again.");
        }
    }

    /**
     * Extracts the email from a verified Google token payload.
     */
    public String extractEmail(JsonNode tokenInfo) {
        if (!tokenInfo.has("email")) {
            throw new BadRequestException("Google token does not contain an email address.");
        }
        return tokenInfo.get("email").asText();
    }

    /**
     * Extracts the display name from a verified Google token payload.
     */
    public String extractName(JsonNode tokenInfo) {
        if (tokenInfo.has("name")) {
            return tokenInfo.get("name").asText();
        }
        // Fallback: combine given_name and family_name
        String given = tokenInfo.has("given_name") ? tokenInfo.get("given_name").asText() : "";
        String family = tokenInfo.has("family_name") ? tokenInfo.get("family_name").asText() : "";
        return (given + " " + family).trim();
    }

    /**
     * Extracts the Google subject ID (unique user identifier) from the token payload.
     */
    public String extractGoogleId(JsonNode tokenInfo) {
        if (!tokenInfo.has("sub")) {
            throw new BadRequestException("Google token does not contain a subject (sub) claim.");
        }
        return tokenInfo.get("sub").asText();
    }
}
