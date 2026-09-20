package com.aicode.reviewer.dto.response;

import com.aicode.reviewer.entity.LanguageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for project responses.
 *
 * @author AI Code Reviewer Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private LanguageType language;
    private boolean archived;
    private long fileCount;
    private long reviewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}