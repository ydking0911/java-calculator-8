package calculator.Exception;

/**
 * 숫자 파싱 관련 예외 (양수가 아닌 값, 오버플로우 등)
 */
public final class NumberParsingException extends IllegalArgumentException {
    private final String token;
    
    public NumberParsingException(String message) {
        super(message);
        this.token = null;
    }
    
    public NumberParsingException(String message, String token) {
        super(message);
        this.token = token;
    }
    
    public String getToken() {
        return token;
    }
    
    public static NumberParsingException nonPositiveValue(String token) {
        return new NumberParsingException("Input contains a non-positive value: " + token, token);
    }
    
    public static NumberParsingException integerOverflow(String token) {
        return new NumberParsingException("Input exceeds integer range: " + token, token);
    }
}
