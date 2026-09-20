package com.aicode.reviewer.repository;

import com.aicode.reviewer.entity.BugDetection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for BugDetection entity operations.
 *
 * @author AI Code Reviewer Team
 */
@Repository
public interface BugDetectionRepository extends JpaRepository<BugDetection, Long> {

    List<BugDetection> findByReviewId(Long reviewId);

    long countByReviewId(Long reviewId);
}