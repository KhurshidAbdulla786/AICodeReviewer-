package com.aicode.reviewer.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class for building AI prompts for code review analysis.
 * Provides structured prompts for different review aspects.
 * 
 * @author AI Code Reviewer Team
 */
@UtilityClass
public class AiPromptBuilder {

    /**
     * Builds a comprehensive code review prompt.
     */
    public static String buildCodeReviewPrompt(String code, String language) {
        return String.format("""
                Analyze the following %s code and provide a comprehensive review:
                
                ```%s
                %s
                ```
                
                Focus on:
                1. Code quality and readability
                2. Security vulnerabilities
                3. Performance issues
                4. Best practices adherence
                5. SOLID principles
                6. Bug detection
                
                Provide structured feedback with actionable recommendations.
                """, language, language.toLowerCase(), code);
    }

    /**
     * Builds a prompt for bug detection.
     */
    public static String buildBugDetectionPrompt(String code, String language) {
        return String.format("""
                Analyze this %s code for potential bugs and security vulnerabilities:
                
                ```%s
                %s
                ```
                
                Identify:
                - Null pointer exceptions
                - Resource leaks
                - SQL injection risks
                - XSS vulnerabilities
                - Logic errors
                - Edge cases not handled
                """, language, language.toLowerCase(), code);
    }

    /**
     * Builds a prompt for code refactoring suggestions.
     */
    public static String buildRefactoringPrompt(String code, String language, String issue) {
        return String.format("""
                Provide refactored code for the following %s snippet to address: %s
                
                Original code:
                ```%s
                %s
                ```
                
                Provide:
                1. Refactored code
                2. Explanation of changes
                3. Benefits of the refactoring
                """, language, issue, language.toLowerCase(), code);
    }

    /**
     * Builds a prompt for code explanation.
     */
    public static String buildExplainCodePrompt(String code, String language) {
        return String.format("""
                Explain the following %s code in detail:
                
                ```%s
                %s
                ```
                
                Include:
                - Purpose and functionality
                - How it works step by step
                - Time and space complexity
                - Potential improvements
                - Common use cases
                """, language, language.toLowerCase(), code);
    }

    /**
     * Builds a prompt for performance optimization.
     */
    public static String buildPerformanceOptimizationPrompt(String code, String language) {
        return String.format("""
                Analyze this %s code for performance optimization opportunities:
                
                ```%s
                %s
                ```
                
                Suggest:
                - Algorithm improvements
                - Data structure optimizations
                - Caching strategies
                - Memory usage reduction
                - Execution speed enhancements
                """, language, language.toLowerCase(), code);
    }
}
