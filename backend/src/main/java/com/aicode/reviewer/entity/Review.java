package com.aicode.reviewer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Review entity representing an AI-powered code review result.
 * Contains all quality scores, code analysis metrics, and AI response data.
 *
 * @author AI Code Reviewer Team
 */
@Entity
@Table(name = "reviews")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"suggestions", "bugDetections", "conversations"})
@ToString(exclude = {"suggestions", "bugDetections", "conversations"})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_file_id", nullable = false)
    private SourceFile sourceFile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ReviewStatus status = ReviewStatus.PENDING;

    @Column(name = "overall_score")
    private Integer overallScore;

    @Column(name = "readability_score")
    private Integer readabilityScore;

    @Column(name = "naming_score")
    private Integer namingScore;

    @Column(name = "style_score")
    private Integer styleScore;

    @Column(name = "solid_score")
    private Integer solidScore;

    @Column(name = "oop_score")
    private Integer oopScore;

    @Column(name = "duplicate_code_score")
    private Integer duplicateCodeScore;

    @Column(name = "dead_code_score")
    private Integer deadCodeScore;

    @Column(name = "exception_handling_score")
    private Integer exceptionHandlingScore;

    @Column(name = "security_score")
    private Integer securityScore;

    @Column(name = "performance_score")
    private Integer performanceScore;

    @Column(columnDefinition = "TEXT")
    private String strengths;

    @Column(columnDefinition = "TEXT")
    private String weaknesses;

    @Column(name = "suggestions_text", columnDefinition = "TEXT")
    private String suggestionsText;

    @Column(name = "cyclomatic_complexity")
    private Integer cyclomaticComplexity;

    @Column(name = "lines_of_code")
    private Integer linesOfCode;

    @Column(name = "number_of_classes")
    private Integer numberOfClasses;

    @Column(name = "number_of_methods")
    private Integer numberOfMethods;

    @Column(name = "time_complexity", length = 50)
    private String timeComplexity;

    @Column(name = "space_complexity", length = 50)
    private String spaceComplexity;

    @Column(name = "maintainability_score")
    private Integer maintainabilityScore;

    @Column(name = "ai_response_json", columnDefinition = "jsonb")
    private String aiResponseJson;

    @Column(name = "ai_model_used", length = 50)
    private String aiModelUsed;

    @Column(name = "processing_time_ms")
    private Long processingTimeMs;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ReviewSuggestion> suggestions = new HashSet<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<BugDetection> bugDetections = new HashSet<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<AIConversation> conversations = new HashSet<>();

    /**
     * Status enum for the review lifecycle.
     */
    public enum ReviewStatus {
        PENDING,
        COMPLETED,
        FAILED
    }
}