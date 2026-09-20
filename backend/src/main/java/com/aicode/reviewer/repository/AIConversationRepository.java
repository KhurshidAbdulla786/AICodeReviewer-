package com.aicode.reviewer.repository;

import com.aicode.reviewer.entity.AIConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for AIConversation entity operations.
 *
 * @author AI Code Reviewer Team
 */
@Repository
public interface AIConversationRepository extends JpaRepository<AIConversation, Long> {

    List<AIConversation> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<AIConversation> findBySourceFileIdOrderByCreatedAtAsc(Long sourceFileId);

    List<AIConversation> findByReviewIdOrderByCreatedAtAsc(Long reviewId);

    long countByUserId(Long userId);
}