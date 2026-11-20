package com.aicode.reviewer.service;

import com.aicode.reviewer.entity.BugDetection;
import com.aicode.reviewer.entity.Review;
import com.aicode.reviewer.repository.BugDetectionRepository;
import com.aicode.reviewer.repository.ReviewRepository;
import com.aicode.reviewer.util.ComplexityAnalyzer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of ReviewMatchingService.
 * Provides bug severity grouping, complexity scoring, similar bug-pattern
 * discovery, and recurring-issue summaries.
 *
 * @author AI Code Reviewer Team
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewMatchingServiceImpl implements ReviewMatchingService {

    private final BugDetectionRepository bugDetectionRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Map<String, List<BugDetection>> matchBugsBySeverity(Long reviewId) {
        List<BugDetection> bugs = bugDetectionRepository.findByReviewId(reviewId);
        Map<String, List<BugDetection>> grouped = new HashMap<>();
        for (BugDetection bug : bugs) {
            String severity = bug.getSeverity() == null ? "UNKNOWN" : bug.getSeverity().toUpperCase();
            grouped.computeIfAbsent(severity, k -> new java.util.ArrayList<>()).add(bug);
        }
        return grouped;
    }

    @Override
    public int calculateComplexityScore(Review review) {
        return ComplexityAnalyzer.calculateFromReview(review);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> findSimilarBugPatterns(Long userId, String bugType) {
        if (bugType == null || bugType.isBlank()) {
            return List.of();
        }
        List<Review> userReviews = reviewRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return userReviews.stream()
                .filter(review -> review.getBugDetections().stream()
                        .anyMatch(bug -> bug.getBugType() != null
                                && bug.getBugType().equalsIgnoreCase(bugType)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getRecurringIssuesSummary(Long userId) {
        List<Review> userReviews = reviewRepository.findByUserIdOrderByCreatedAtDesc(userId);
        Map<String, Long> summary = new HashMap<>();
        for (Review review : userReviews) {
            for (BugDetection bug : review.getBugDetections()) {
                String type = bug.getBugType() == null ? "UNKNOWN" : bug.getBugType();
                summary.merge(type, 1L, Long::sum);
            }
        }
        return summary;
    }
}