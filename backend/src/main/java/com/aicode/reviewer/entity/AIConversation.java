package com.aicode.reviewer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * AI conversation entity storing chat interactions between users and the AI.
 * Supports contextual conversations tied to specific reviews or source files.
 *
 * @author AI Code Reviewer Team
 */
@Entity
@Table(name = "ai_conversations")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIConversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_file_id")
    private SourceFile sourceFile;

    @Column(name = "user_message", nullable = false, columnDefinition = "TEXT")
    private String userMessage;

    @Column(name = "ai_response", nullable = false, columnDefinition = "TEXT")
    private String aiResponse;

    @Column(name = "message_type", nullable = false, length = 50)
    @Builder.Default
    private String messageType = "CHAT";

    @Column(name = "tokens_used")
    private Integer tokensUsed;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}