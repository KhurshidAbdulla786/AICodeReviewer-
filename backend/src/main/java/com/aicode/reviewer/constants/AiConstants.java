package com.aicode.reviewer.constants;

/**
 * Constants for AI-powered code review system.
 * Contains prompt templates, scoring thresholds, and configuration values.
 * 
 * @author AI Code Reviewer Team
 */
public final class AiConstants {

    private AiConstants() {
        // Utility class
    }

    // Scoring Thresholds
    public static final int EXCELLENT_SCORE = 90;
    public static final int GOOD_SCORE = 75;
    public static final int FAIR_SCORE = 60;
    public static final int POOR_SCORE = 40;

    // Review Categories
    public static final String CATEGORY_READABILITY = "READABILITY";
    public static final String CATEGORY_NAMING = "NAMING";
    public static final String CATEGORY_STYLE = "STYLE";
    public static final String CATEGORY_SOLID = "SOLID";
    public static final String CATEGORY_OOP = "OOP";
    public static final String CATEGORY_PERFORMANCE = "PERFORMANCE";
    public static final String CATEGORY_SECURITY = "SECURITY";
    public static final String CATEGORY_EXCEPTION_HANDLING = "EXCEPTION_HANDLING";

    // Bug Severity Levels
    public static final String SEVERITY_CRITICAL = "CRITICAL";
    public static final String SEVERITY_HIGH = "HIGH";
    public static final String SEVERITY_MEDIUM = "MEDIUM";
    public static final String SEVERITY_LOW = "LOW";
    public static final String SEVERITY_INFO = "INFO";

    // Bug Types
    public static final String BUG_NULL_POINTER = "NULL_POINTER";
    public static final String BUG_INFINITE_LOOP = "INFINITE_LOOP";
    public static final String BUG_SQL_INJECTION = "SQL_INJECTION";
    public static final String BUG_XSS = "XSS";
    public static final String BUG_MEMORY_LEAK = "MEMORY_LEAK";
    public static final String BUG_RESOURCE_LEAK = "RESOURCE_LEAK";
    public static final String BUG_UNUSED_VARIABLE = "UNUSED_VARIABLE";
    public static final String BUG_UNREACHABLE_CODE = "UNREACHABLE_CODE";
    public static final String BUG_INDEX_OUT_OF_BOUNDS = "INDEX_OUT_OF_BOUNDS";
    public static final String BUG_DIVISION_BY_ZERO = "DIVISION_BY_ZERO";

    // AI Model Configuration
    public static final String DEFAULT_MODEL = "gpt-4";
    public static final double DEFAULT_TEMPERATURE = 0.7;
    public static final int MAX_TOKENS = 4096;
    public static final int TIMEOUT_SECONDS = 30;

    // Code Analysis Limits
    public static final int MAX_CODE_LENGTH = 50000; // characters
    public static final int MIN_CODE_LENGTH = 10; // characters
    public static final int MAX_FILE_SIZE_MB = 5;

    // Review System Messages
    public static final String SYSTEM_ROLE_REVIEWER = """
            You are an expert senior software engineer performing comprehensive code reviews.
            Analyze code quality, security, performance, and best practices.
            Provide actionable feedback with specific recommendations.
            """;

    public static final String SYSTEM_ROLE_BUG_DETECTOR = """
            You are an expert bug detection system.
            Identify potential bugs, security vulnerabilities, and code smells.
            Prioritize findings by severity and provide fix recommendations.
            """;

    public static final String SYSTEM_ROLE_CODE_EXPLAINER = """
            You are an expert code explanation assistant.
            Explain code clearly, covering purpose, functionality, and complexity.
            Help developers understand code structure and potential improvements.
            """;

    // Response Format Instructions
    public static final String JSON_FORMAT_INSTRUCTION = """
            Return ONLY valid JSON without markdown code blocks.
            Ensure all required fields are present with appropriate data types.
            """;
}
