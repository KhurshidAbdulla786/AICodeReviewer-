package com.aicode.reviewer.repository;

import com.aicode.reviewer.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Project entity operations.
 *
 * @author AI Code Reviewer Team
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByUserIdOrderByUpdatedAtDesc(Long userId);

    Optional<Project> findByIdAndUserId(Long id, Long userId);

    long countByUserId(Long userId);

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.sourceFiles WHERE p.id = :id AND p.user.id = :userId")
    Optional<Project> findByIdWithFiles(@Param("id") Long id, @Param("userId") Long userId);

    @Query("SELECT p FROM Project p WHERE p.user.id = :userId AND p.archived = false ORDER BY p.updatedAt DESC")
    List<Project> findActiveProjectsByUserId(@Param("userId") Long userId);

    void deleteByIdAndUserId(Long id, Long userId);
}