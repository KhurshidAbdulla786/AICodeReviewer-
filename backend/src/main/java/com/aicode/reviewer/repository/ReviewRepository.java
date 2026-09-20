package com.aicode.reviewer.repository;

import com.aicode.reviewer.entity.Review;
import com.aicode.reviewer.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Review entity operations.
 *
 * @author AI Code Reviewer Team
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Review> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.suggestions LEFT JOIN FETCH r.bugDetections WHERE r.id = :id")
    Optional<Review> findByIdWithDetails(@Param("id") Long id);

    long countByUserId(Long userId);

    long countByUserIdAndCreatedAtAfter(Long userId, LocalDateTime after);

    @Query("SELECT AVG(r.overallScore) FROM Review r WHERE r.user.id = :userId AND r.overallScore IS NOT NULL")
    Double averageScoreByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.createdAt >= :since ORDER BY r.createdAt DESC")
    List<Review> findRecentReviewsByUserId(@Param("userId") Long userId, @Param("since") LocalDateTime since);

    @Query("SELECT r FROM Review r WHERE r.status = 'COMPLETED' AND r.overallScore IS NOT NULL ORDER BY r.createdAt DESC")
    List<Review> findAllCompleted(Pageable pageable);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.createdAt >= :since")
    long countReviewsSince(@Param("since") LocalDateTime since);

    @Query("SELECT r from Review r WHERE r.sourceFile.id = :sourceFileId ORDER BY r.createdAt DESC")
    List<Review> findBySourceFileId(@Param("sourceFileId") Long sourceFileId);
}