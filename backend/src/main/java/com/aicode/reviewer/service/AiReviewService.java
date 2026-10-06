package com.aicode.reviewer.service;

import com.aicode.reviewer.dto.response.ReviewResponse;
import com.aicode.reviewer.entity.Review;
import com.aicode.reviewer.entity.SourceFile;

/**
 * Service interface for AI-powered code review functionality.
 * Integrates with OpenAI/Gemini to analyze code and produce structured reviews.
 *
 * @author AI Code Reviewer Team
 */
public interface AiReviewService {

    /**
     * Submits a source file for AI-powered code review.
     *
     * @param sourceFile the source file to review
     * @param userId the ID of the user requesting the review
     * @return the completed review with all analysis results
     */
    Review reviewCode(SourceFile sourceFile, Long userId);

    /**
     * Generates refactored code for a specific review suggestion.
     *
     * @param sourceFileId the ID of the source file
     * @param suggestionId the ID of the suggestion
     * @return the refactored code with explanation
     */
    String generateRefactoredCode(Long sourceFileId, Long suggestionId);

    /**
     * Sends a chat message to the AI with code context.
     *
     * @param userId the user ID
     * @param message the user's message
     * @param sourceFileId optional source file for context
     * @param reviewId optional review for context
     * @return the AI response
     */
    String chatWithAi(Long userId, String message, Long sourceFileId, Long reviewId);

    /**
     * Explains a specific method or code block in detail.
     *
     * @param code the code snippet to explain
     * @param language the programming language
     * @return detailed explanation
     */
    String explainCode(String code, String language);
}