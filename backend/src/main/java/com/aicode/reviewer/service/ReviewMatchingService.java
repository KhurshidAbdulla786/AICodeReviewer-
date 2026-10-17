package com.aicode.reviewer.service;

import com.aicode.reviewer.entity.BugDetection;
import com.aicode.reviewer.entity.Review;

import java.util.List;
import java.util.Map;

/**
 * Service interface for review matching operations.
 * Handles code complexity analysis and bug detection pattern matching.
 *
 * @author AI Code Reviewer Team
 */
public interface ReviewMatchingService {

    /**
     * Matches and categorizes detected bugs by severity.
     *
     * @param reviewId the review ID
     * @return map of severity level to list of bug detections
     */
    Map<String, List<BugDetection>> matchBugsBySeverity(Long reviewId);

    /**
     * Calculates a complexity score for the reviewed code.
     *
     * @param review the review entity with analysis data
     * @return complexity score between 0-100
     */
    int calculateComplexityScore(Review review);

    /**
     * Finds similar bug patterns across the user's past reviews.
     *
     * @param userId the user ID
     * @param bugType the bug type to search for
     * @return list of reviews containing similar bug patterns
     */
    List<Review> findSimilarBugPatterns(Long userId, String bugType);

    /**
     * Generates a summary of recurring issues for a user.
     *
     * @param userId the user ID
     * @return map of bug type to occurrence count
     */
    Map<String, Long> getRecurringIssuesSummary(Long userId);
}
