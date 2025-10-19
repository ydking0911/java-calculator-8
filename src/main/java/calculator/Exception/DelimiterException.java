package calculator.Exception;

/**
 * 구분자 관련 예외 (형식, 길이 등)
 */
public final class DelimiterException extends IllegalArgumentException {
    public DelimiterException(String message) {
        super(message);
    }
    
    public static DelimiterException invalidFormat() {
        return new DelimiterException("Custom delimiter format is invalid.");
    }
    
    public static DelimiterException invalidLength() {
        return new DelimiterException("Custom delimiter must be a single character.");
    }
}
