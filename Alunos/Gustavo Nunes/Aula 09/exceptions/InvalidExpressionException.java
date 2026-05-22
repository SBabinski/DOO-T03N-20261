package exceptions;

// Custom exception for calculator expressions
public class InvalidExpressionException extends RuntimeException {
    
    // Constructor for custom exception
    public InvalidExpressionException(String message) {
        super(message);
    }
}
