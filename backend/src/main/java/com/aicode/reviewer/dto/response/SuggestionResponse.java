package com.aicode.reviewer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for review suggestion responses.
 *
 * @author AI Code Reviewer Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionResponse {
    private Long id;
    private String category;
    private String severity;
    private Integer lineStart;
    private Integer lineEnd;
    private String message;
    private String originalCode;
    private String suggestedCode;
    private String explanation;
    private boolean applied;
}