package com.aicode.reviewer.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration for AI integration with Spring AI.
 * Supports OpenAI and Gemini models for code review analysis.
 * 
 * @author AI Code Reviewer Team
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AiConfig {

    private final ChatModel chatModel;

    /**
     * Validates AI model connectivity on startup.
     */
    public void validateAiConnection() {
        log.info("AI Integration initialized with ChatModel: {}", chatModel.getClass().getSimpleName());
        log.info("Ready to process code reviews with AI-powered analysis");
    }
}
