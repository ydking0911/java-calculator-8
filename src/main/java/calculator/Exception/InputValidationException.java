package calculator.Exception;

/**
 * 입력 검증 관련 예외 (null, 빈 값, 빈 토큰 등)
 */
public final class InputValidationException extends IllegalArgumentException {
    public InputValidationException(String message) {
        super(message);
    }
    
    public static InputValidationException nullInput() {
        return new InputValidationException("Input cannot be null.");
    }
    
    public static InputValidationException emptyInput() {
        return new InputValidationException("Input values cannot be empty.");
    }
    
    public static InputValidationException emptyToken() {
        return new InputValidationException("Input contains an empty value.");
    }
}
