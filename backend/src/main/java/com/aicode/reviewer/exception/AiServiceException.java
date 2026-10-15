package com.aicode.reviewer.exception;

/**
 * Exception thrown when AI service fails or returns invalid response.
 * 
 * @author AI Code Reviewer Team
 */
public class AiServiceException extends RuntimeException {
    
    public AiServiceException(String message) {
        super(message);
    }
    
    public AiServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
