package com.aicode.reviewer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for bug detection responses.
 *
 * @author AI Code Reviewer Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BugDetectionResponse {
    private Long id;
    private String bugType;
    private String severity;
    private Integer lineNumber;
    private String description;
    private String codeSnippet;
    private String recommendation;
}