package com.aicode.reviewer.service;

import com.aicode.reviewer.entity.*;
import com.aicode.reviewer.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Implementation of AiReviewService using Spring AI with OpenAI/Gemini.
 * Processes code through AI models and returns structured review results.
 *
 * @author AI Code Reviewer Team
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AiReviewServiceImpl implements AiReviewService {

    private final ChatModel chatModel;
    private final ReviewRepository reviewRepository;
    private final SourceFileRepository sourceFileRepository;
    private final UserRepository userRepository;
    private final ReviewSuggestionRepository suggestionRepository;
    private final BugDetectionRepository bugDetectionRepository;
    private final AIConversationRepository conversationRepository;
    private final ObjectMapper objectMapper;

    private static final String REVIEW_SYSTEM_PROMPT = """
            You are an expert senior software engineer performing a comprehensive code review.
            Analyze the provided code and return a JSON response with the following structure (ONLY valid JSON, no markdown):
            {
              "overallScore": <0-100>,
              "readabilityScore": <0-100>,
              "namingScore": <0-100>,
              "styleScore": <0-100>,
              "solidScore": <0-100>,
              "oopScore": <0-100>,
              "duplicateCodeScore": <0-100>,
              "deadCodeScore": <0-100>,
              "exceptionHandlingScore": <0-100>,
              "securityScore": <0-100>,
              "performanceScore": <0-100>,
              "strengths": "comma-separated strengths",
              "weaknesses": "comma-separated weaknesses",
              "suggestions": "overall suggestions text",
              "cyclomaticComplexity": <number>,
              "linesOfCode": <number>,
              "numberOfClasses": <number>,
              "numberOfMethods": <number>,
              "timeComplexity": "e.g. O(n)",
              "spaceComplexity": "e.g. O(1)",
              "maintainabilityScore": <0-100>,
              "bugs": [
                {
                  "bugType": "NULL_POINTER|INFINITE_LOOP|SQL_INJECTION|XSS|MEMORY_LEAK|RESOURCE_LEAK|UNUSED_VARIABLE|UNREACHABLE_CODE|INDEX_OUT_OF_BOUNDS|DIVISION_BY_ZERO",
                  "severity": "LOW|MEDIUM|HIGH|CRITICAL",
                  "lineNumber": <number>,
                  "description": "description of the bug",
                  "codeSnippet": "relevant code",
                  "recommendation": "fix recommendation"
                }
              ],
              "suggestions": [
                {
                  "category": "READABILITY|NAMING|STYLE|SOLID|OOP|PERFORMANCE|SECURITY|EXCEPTION_HANDLING",
                  "severity": "INFO|WARNING|IMPORTANT|CRITICAL",
                  "lineStart": <number>,
                  "lineEnd": <number>,
                  "message": "suggestion description",
                  "originalCode": "original code snippet",
                  "suggestedCode": "improved code snippet",
                  "explanation": "why this change improves the code"
                }
              ]
            }
            Be thorough and specific. Provide actionable feedback.
            """;

    private static final String CHAT_SYSTEM_PROMPT = """
            You are an expert programming assistant specializing in code analysis, 
            optimization, and best practices. You help developers understand their code,
            find bugs, improve performance, and follow design patterns.
            Provide clear, concise, and actionable responses.
            """;

    @Override
    @Transactional
    public Review reviewCode(SourceFile sourceFile, Long userId) {
        long startTime = System.currentTimeMillis();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = Review.builder()
                .user(user)
                .sourceFile(sourceFile)
                .status(Review.ReviewStatus.PENDING)
                .aiModelUsed("gpt-4")
                .build();
        review = reviewRepository.save(review);

        try {
            String aiResponse = callAiForReview(sourceFile.getContent(), sourceFile.getLanguage().name());
            long processingTime = System.currentTimeMillis() - startTime;

            JsonNode json = objectMapper.readTree(aiResponse);
            updateReviewFromJson(review, json, processingTime);

            // Parse and save suggestions
            if (json.has("suggestions") && json.get("suggestions").isArray()) {
                for (JsonNode sug : json.get("suggestions")) {
                    ReviewSuggestion suggestion = ReviewSuggestion.builder()
                            .review(review)
                            .category(sug.has("category") ? sug.get("category").asText() : "GENERAL")
                            .severity(sug.has("severity") ? sug.get("severity").asText() : "INFO")
                            .lineStart(sug.has("lineStart") ? sug.get("lineStart").asInt() : null)
                            .lineEnd(sug.has("lineEnd") ? sug.get("lineEnd").asInt() : null)
                            .message(sug.has("message") ? sug.get("message").asText() : "")
                            .originalCode(sug.has("originalCode") ? sug.get("originalCode").asText() : null)
                            .suggestedCode(sug.has("suggestedCode") ? sug.get("suggestedCode").asText() : null)
                            .explanation(sug.has("explanation") ? sug.get("explanation").asText() : null)
                            .build();
                    suggestionRepository.save(suggestion);
                    review.getSuggestions().add(suggestion);
                }
            }

            // Parse and save bug detections
            if (json.has("bugs") && json.get("bugs").isArray()) {
                for (JsonNode bug : json.get("bugs")) {
                    BugDetection bugDetection = BugDetection.builder()
                            .review(review)
                            .bugType(bug.has("bugType") ? bug.get("bugType").asText() : "UNKNOWN")
                            .severity(bug.has("severity") ? bug.get("severity").asText() : "MEDIUM")
                            .lineNumber(bug.has("lineNumber") ? bug.get("lineNumber").asInt() : null)
                            .description(bug.has("description") ? bug.get("description").asText() : "")
                            .codeSnippet(bug.has("codeSnippet") ? bug.get("codeSnippet").asText() : null)
                            .recommendation(bug.has("recommendation") ? bug.get("recommendation").asText() : null)
                            .build();
                    bugDetectionRepository.save(bugDetection);
                    review.getBugDetections().add(bugDetection);
                }
            }

            review.setStatus(Review.ReviewStatus.COMPLETED);
            review.setAiResponseJson(objectMapper.writeValueAsString(json));
            review = reviewRepository.save(review);

        } catch (Exception e) {
            log.error("AI review failed for file {}: {}", sourceFile.getFilename(), e.getMessage());
            review.setStatus(Review.ReviewStatus.FAILED);
            review = reviewRepository.save(review);
        }

        return review;
    }

    @Override
    public String generateRefactoredCode(Long sourceFileId, Long suggestionId) {
        SourceFile sourceFile = sourceFileRepository.findById(sourceFileId)
                .orElseThrow(() -> new RuntimeException("Source file not found"));
        ReviewSuggestion suggestion = suggestionRepository.findById(suggestionId)
                .orElseThrow(() -> new RuntimeException("Suggestion not found"));

        String prompt = String.format("""
                Refactor the following code to improve it based on this suggestion:
                
                Original code:
                %s
                
                Suggestion: %s
                Category: %s
                
                Provide the refactored code and explain each improvement.
                """, sourceFile.getContent(), suggestion.getMessage(), suggestion.getCategory());

        ChatResponse response = chatModel.call(new Prompt(
                new SystemMessage("You are an expert code refactoring assistant."),
                new UserMessage(prompt)
        ));
        return response.getResult().getOutput().getText();
    }

    @Override
    @Transactional
    public String chatWithAi(Long userId, String message, Long sourceFileId, Long reviewId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StringBuilder contextPrompt = new StringBuilder(message);

        if (sourceFileId != null) {
            SourceFile sourceFile = sourceFileRepository.findById(sourceFileId)
                    .orElse(null);
            if (sourceFile != null) {
                contextPrompt.append("\n\nContext - Source file: ")
                        .append(sourceFile.getFilename())
                        .append("\n```\n").append(sourceFile.getContent()).append("\n```");
            }
        }

        ChatResponse response = chatModel.call(new Prompt(
                new SystemMessage(CHAT_SYSTEM_PROMPT),
                new UserMessage(contextPrompt.toString())
        ));

        String aiResponse = response.getResult().getOutput().getText();

        AIConversation conversation = AIConversation.builder()
                .user(user)
                .userMessage(message)
                .aiResponse(aiResponse)
                .messageType("CHAT")
                .review(sourceFileId != null ? null : null) // simplified
                .build();
        conversationRepository.save(conversation);

        return aiResponse;
    }

    @Override
    public String explainCode(String code, String language) {
        String prompt = String.format("""
                Explain the following %s code in detail.
                Include: purpose, how it works, complexity, potential improvements:
                
                ```%s
                %s
                ```
                """, language, language.toLowerCase(), code);

        ChatResponse response = chatModel.call(new Prompt(
                new SystemMessage("You are an expert code explanation assistant."),
                new UserMessage(prompt)
        ));
        return response.getResult().getOutput().getText();
    }

    /**
     * Calls the AI model with the review prompt.
     */
    private String callAiForReview(String code, String language) {
        String prompt = String.format("""
                Review this %s code:
                                
                ```%s
                %s
                ```
                                
                Return the JSON review as specified in the system prompt.
                """, language, language.toLowerCase(), code);

        ChatResponse response = chatModel.call(new Prompt(
                new SystemMessage(REVIEW_SYSTEM_PROMPT),
                new UserMessage(prompt)
        ));

        return response.getResult().getOutput().getText();
    }

    /**
     * Updates the review entity with parsed JSON scores.
     */
    private void updateReviewFromJson(Review review, JsonNode json, long processingTimeMs) {
        review.setOverallScore(getIntSafely(json, "overallScore"));
        review.setReadabilityScore(getIntSafely(json, "readabilityScore"));
        review.setNamingScore(getIntSafely(json, "namingScore"));
        review.setStyleScore(getIntSafely(json, "styleScore"));
        review.setSolidScore(getIntSafely(json, "solidScore"));
        review.setOopScore(getIntSafely(json, "oopScore"));
        review.setDuplicateCodeScore(getIntSafely(json, "duplicateCodeScore"));
        review.setDeadCodeScore(getIntSafely(json, "deadCodeScore"));
        review.setExceptionHandlingScore(getIntSafely(json, "exceptionHandlingScore"));
        review.setSecurityScore(getIntSafely(json, "securityScore"));
        review.setPerformanceScore(getIntSafely(json, "performanceScore"));
        review.setStrengths(getStringSafely(json, "strengths"));
        review.setWeaknesses(getStringSafely(json, "weaknesses"));
        review.setSuggestionsText(getStringSafely(json, "suggestions"));
        review.setCyclomaticComplexity(getIntSafely(json, "cyclomaticComplexity"));
        review.setLinesOfCode(getIntSafely(json, "linesOfCode"));
        review.setNumberOfClasses(getIntSafely(json, "numberOfClasses"));
        review.setNumberOfMethods(getIntSafely(json, "numberOfMethods"));
        review.setTimeComplexity(getStringSafely(json, "timeComplexity"));
        review.setSpaceComplexity(getStringSafely(json, "spaceComplexity"));
        review.setMaintainabilityScore(getIntSafely(json, "maintainabilityScore"));
        review.setProcessingTimeMs(processingTimeMs);
    }

    private int getIntSafely(JsonNode json, String field) {
        return json.has(field) && !json.get(field).isNull() ? json.get(field).asInt() : 0;
    }

    private String getStringSafely(JsonNode json, String field) {
        return json.has(field) && !json.get(field).isNull() ? json.get(field).asText() : null;
    }
}