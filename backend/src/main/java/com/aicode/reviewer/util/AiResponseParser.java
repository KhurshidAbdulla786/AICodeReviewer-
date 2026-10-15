package com.aicode.reviewer.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for parsing AI response JSON.
 * Handles extraction of review data from AI model responses.
 * 
 * @author AI Code Reviewer Team
 */
@UtilityClass
@Slf4j
public class AiResponseParser {

    /**
     * Extracts clean JSON from AI response text.
     * Removes markdown code blocks and extra formatting.
     */
    public static String extractJson(String aiResponse) {
        if (aiResponse == null || aiResponse.isBlank()) {
            return "{}";
        }

        String cleaned = aiResponse.trim();

        // Remove markdown code blocks
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        } else if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3);
        }

        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }

        return cleaned.trim();
    }

    /**
     * Safely extracts integer value from JSON node.
     */
    public static int getIntSafely(JsonNode json, String field, int defaultValue) {
        if (json == null || !json.has(field) || json.get(field).isNull()) {
            return defaultValue;
        }
        try {
            return json.get(field).asInt(defaultValue);
        } catch (Exception e) {
            log.warn("Failed to parse integer field '{}': {}", field, e.getMessage());
            return defaultValue;
        }
    }

    /**
     * Safely extracts string value from JSON node.
     */
    public static String getStringSafely(JsonNode json, String field, String defaultValue) {
        if (json == null || !json.has(field) || json.get(field).isNull()) {
            return defaultValue;
        }
        try {
            return json.get(field).asText(defaultValue);
        } catch (Exception e) {
            log.warn("Failed to parse string field '{}': {}", field, e.getMessage());
            return defaultValue;
        }
    }

    /**
     * Validates if the AI response contains required fields.
     */
    public static boolean validateReviewResponse(JsonNode json) {
        if (json == null) {
            return false;
        }

        String[] requiredFields = {
            "overallScore", "readabilityScore", "securityScore",
            "performanceScore", "suggestions"
        };

        for (String field : requiredFields) {
            if (!json.has(field)) {
                log.warn("Missing required field in AI response: {}", field);
                return false;
            }
        }

        return true;
    }

    /**
     * Calculates average score from multiple metrics.
     */
    public static int calculateAverageScore(int... scores) {
        if (scores == null || scores.length == 0) {
            return 0;
        }

        int sum = 0;
        int count = 0;

        for (int score : scores) {
            if (score >= 0 && score <= 100) {
                sum += score;
                count++;
            }
        }

        return count > 0 ? sum / count : 0;
    }
}
