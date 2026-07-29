package com.aicode.reviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main entry point for the AI Code Reviewer application.
 * Enables JPA auditing for automatic timestamp management.
 *
 * @author AI Code Reviewer Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
public class AiCodeReviewerApplication {

    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AiCodeReviewerApplication.class, args);
    }
}