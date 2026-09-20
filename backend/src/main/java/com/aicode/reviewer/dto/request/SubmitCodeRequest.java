package com.aicode.reviewer.dto.request;

import com.aicode.reviewer.entity.LanguageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for submitting code for AI review.
 *
 * @author AI Code Reviewer Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitCodeRequest {

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotBlank(message = "Code content is required")
    private String code;

    @NotBlank(message = "Filename is required")
    private String filename;

    @NotNull(message = "Language is required")
    private LanguageType language;
}