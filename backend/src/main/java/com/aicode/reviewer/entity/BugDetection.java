package com.aicode.reviewer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Bug detection entity representing potential bugs or security vulnerabilities
 * identified during the AI code review process.
 *
 * @author AI Code Reviewer Team
 */
@Entity
@Table(name = "bug_detections")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BugDetection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "bug_type", nullable = false, length = 50)
    private String bugType;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String severity = "MEDIUM";

    @Column(name = "line_number")
    private Integer lineNumber;

    @Lob
    @Column(nullable = false)
    private String description;

    @Column(name = "code_snippet", columnDefinition = "TEXT")
    private String codeSnippet;

    @Column(columnDefinition = "TEXT")
    private String recommendation;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}