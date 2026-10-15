package com.aicode.reviewer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for AI chat interactions.
 * Contains AI-generated responses for code-related queries.
 * 
 * @author AI Code Reviewer Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatResponse {
    
    private String message;
    private String conversationId;
    private Long timestamp;
    private String context; // Source file or review context
    private Boolean isCodeSnippet;
    
    public static AiChatResponse of(String message) {
        return AiChatResponse.builder()
                .message(message)
                .timestamp(System.currentTimeMillis())
                .isCodeSnippet(false)
                .build();
    }
}
