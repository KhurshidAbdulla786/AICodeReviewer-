package com.aicode.reviewer.util;

import com.aicode.reviewer.entity.Review;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for calculating code complexity metrics.
 * Used by ReviewMatchingService to evaluate and match reviews.
 *
 * @author AI Code Reviewer Team
 */
@UtilityClass
@Slf4j
public class ComplexityAnalyzer {

    private static final int MAX_SCORE = 100;

    /**
     * Calculates weighted complexity score from review metrics.
     *
     * @param cyclomaticComplexity cyclomatic complexity value
     * @param linesOfCode          total lines of code
     * @param numberOfMethods      number of methods
     * @param numberOfClasses      number of classes
     * @return complexity score between 0-100 (higher = more complex)
     */
    public static int calculateComplexityScore(
            int cyclomaticComplexity,
            int linesOfCode,
            int numberOfMethods,
            int numberOfClasses) {

        double ccWeight = normalizeMetric(cyclomaticComplexity, 1, 50) * 0.35;
        double locWeight = normalizeMetric(linesOfCode, 10, 2000) * 0.25;
        double methodWeight = normalizeMetric(numberOfMethods, 1, 50) * 0.25;
        double classWeight = normalizeMetric(numberOfClasses, 1, 20) * 0.15;

        int score = (int) Math.round((ccWeight + locWeight + methodWeight + classWeight) * MAX_SCORE);
        return Math.min(score, MAX_SCORE);
    }

    /**
     * Calculates complexity score directly from a Review entity.
     */
    public static int calculateFromReview(Review review) {
        if (review == null) {
            return 0;
        }

        return calculateComplexityScore(
                safeInt(review.getCyclomaticComplexity()),
                safeInt(review.getLinesOfCode()),
                safeInt(review.getNumberOfMethods()),
                safeInt(review.getNumberOfClasses())
        );
    }

    /**
     * Determines complexity level label from score.
     */
    public static String getComplexityLevel(int score) {
        if (score >= 80) return "VERY_HIGH";
        if (score >= 60) return "HIGH";
        if (score >= 40) return "MODERATE";
        if (score >= 20) return "LOW";
        return "MINIMAL";
    }

    /**
     * Normalizes a metric value to a 0.0 - 1.0 range.
     */
    private static double normalizeMetric(int value, int min, int max) {
        if (value <= min) return 0.0;
        if (value >= max) return 1.0;
        return (double) (value - min) / (max - min);
    }

    private static int safeInt(Integer value) {
        return value != null ? value : 0;
    }
}
