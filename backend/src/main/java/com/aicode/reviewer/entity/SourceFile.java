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
 * Source file entity representing an uploaded or pasted code file.
 * Each file belongs to a project and can have multiple reviews.
 *
 * @author AI Code Reviewer Team
 */
@Entity
@Table(name = "source_files")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"reviews", "conversations"})
@ToString(exclude = {"reviews", "conversations"})
public class SourceFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false, length = 255)
    private String filename;

    @Column(name = "file_path", length = 1000)
    private String filePath;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LanguageType language;

    @Column(name = "file_size", nullable = false)
    @Builder.Default
    private long fileSize = 0;

    @Column(length = 64)
    private String checksum;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "sourceFile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "sourceFile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<AIConversation> conversations = new HashSet<>();
}