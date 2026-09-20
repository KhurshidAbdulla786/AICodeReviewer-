package com.aicode.reviewer.repository;

import com.aicode.reviewer.entity.ReviewSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for ReviewSuggestion entity operations.
 *
 * @author AI Code Reviewer Team
 */
@Repository
public interface ReviewSuggestionRepository extends JpaRepository<ReviewSuggestion, Long> {

    List<ReviewSuggestion> findByReviewId(Long reviewId);

    long countByReviewId(Long reviewId);
}