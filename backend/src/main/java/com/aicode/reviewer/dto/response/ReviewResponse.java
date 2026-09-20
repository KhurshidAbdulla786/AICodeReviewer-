package com.aicode.reviewer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for review responses containing full AI analysis results.
 *
 * @author AI Code Reviewer Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private Long sourceFileId;
    private String filename;
    private String status;
    private Integer overallScore;
    private Integer readabilityScore;
    private Integer namingScore;
    private Integer styleScore;
    private Integer solidScore;
    private Integer oopScore;
    private Integer duplicateCodeScore;
    private Integer deadCodeScore;
    private Integer exceptionHandlingScore;
    private Integer securityScore;
    private Integer performanceScore;
    private String strengths;
    private String weaknesses;
    private String suggestionsText;
    private Integer cyclomaticComplexity;
    private Integer linesOfCode;
    private Integer numberOfClasses;
    private Integer numberOfMethods;
    private String timeComplexity;
    private String spaceComplexity;
    private Integer maintainabilityScore;
    private String aiModelUsed;
    private Long processingTimeMs;
    private List<SuggestionResponse> suggestions;
    private List<BugDetectionResponse> bugs;
    private LocalDateTime createdAt;
}