package com.aicode.reviewer.repository;

import com.aicode.reviewer.entity.SourceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for SourceFile entity operations.
 *
 * @author AI Code Reviewer Team
 */
@Repository
public interface SourceFileRepository extends JpaRepository<SourceFile, Long> {

    List<SourceFile> findByProjectIdOrderByCreatedAtDesc(Long projectId);

    Optional<SourceFile> findByIdAndProjectId(Long id, Long projectId);

    long countByProjectId(Long projectId);

    long countByProjectUserId(Long userId);
}