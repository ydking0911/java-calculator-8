package calculator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import calculator.Exception.DelimiterException;
import calculator.Exception.InputValidationException;
import calculator.Exception.NumberParsingException;

class StringParserTest {

    private final StringParser parser = new StringParser();

    @Test
    @DisplayName("쉼표로 구분된 양의 정수들을 파싱")
    void parseCommaSeparatedValues() {
        // Given: 쉼표로 구분된 양의 정수 문자열
        // When: 파싱을 수행
        List<Integer> numbers = parser.parse("1,2,3");
        // Then: 정확한 정수 리스트가 반환되어야 함
        assertThat(numbers).containsExactly(1, 2, 3);
    }

    @Test
    @DisplayName("커스텀 구분자가 있는 경우 파싱")
    void parseCustomDelimiter() {
        // Given: 커스텀 구분자(;)가 있는 문자열
        // When: 파싱을 수행
        List<Integer> numbers = parser.parse("//;\n4;5;6");
        // Then: 커스텀 구분자로 분리된 정수 리스트가 반환되어야 함
        assertThat(numbers).containsExactly(4, 5, 6);
    }

    @Test
    @DisplayName("리터럴 줄바꿈 시퀀스를 사용한 커스텀 구분자 파싱")
    void parseCustomDelimiterWithEscapedNewline() {
        // Given: 리터럴 \n을 사용한 커스텀 구분자 문자열
        // When: 파싱을 수행
        List<Integer> numbers = parser.parse("//;\\n7;8");
        // Then: 커스텀 구분자로 분리된 정수 리스트가 반환되어야 함
        assertThat(numbers).containsExactly(7, 8);
    }

    @Test
    @DisplayName("양수가 아닌 토큰을 거부")
    void parseRejectsNonPositiveNumbers() {
        // Given: 음수를 포함한 문자열
        // When & Then: NumberParsingException이 발생해야 함
        assertThatThrownBy(() -> parser.parse("-1,2"))
            .isInstanceOf(NumberParsingException.class)
            .hasMessageContaining("non-positive value");
    }

    @Test
    @DisplayName("빈 토큰을 포함한 입력을 거부")
    void parseRejectsEmptyTokens() {
        // Given: 빈 토큰을 포함한 문자열
        // When & Then: InputValidationException이 발생해야 함
        assertThatThrownBy(() -> parser.parse("1,,2"))
            .isInstanceOf(InputValidationException.class)
            .hasMessageContaining("empty value");
    }

    @Test
    @DisplayName("null 입력을 거부")
    void parseRejectsNullInput() {
        // Given: null 입력
        // When & Then: InputValidationException이 발생해야 함
        assertThatThrownBy(() -> parser.parse(null))
            .isInstanceOf(InputValidationException.class)
            .hasMessageContaining("cannot be null");
    }

    @Test
    @DisplayName("커스텀 구분자 후 빈 본문을 거부")
    void parseRejectsEmptyBodyAfterCustomDelimiter() {
        // Given: 커스텀 구분자 후 빈 본문이 있는 문자열
        // When & Then: DelimiterException이 발생해야 함
        assertThatThrownBy(() -> parser.parse("//;\n"))
            .isInstanceOf(DelimiterException.class)
            .hasMessageContaining("format is invalid");
    }

    @Test
    @DisplayName("잘못된 커스텀 구분자 형식을 거부")
    void parseRejectsInvalidDelimiterFormat() {
        // Given: 잘못된 커스텀 구분자 형식의 문자열
        // When & Then: DelimiterException이 발생해야 함
        assertThatThrownBy(() -> parser.parse("//;"))
            .isInstanceOf(DelimiterException.class)
            .hasMessageContaining("format is invalid");
    }

    @Test
    @DisplayName("잘못된 커스텀 구분자 길이를 거부")
    void parseRejectsInvalidDelimiterLength() {
        // Given: 두 글자 이상의 커스텀 구분자가 있는 문자열
        // When & Then: DelimiterException이 발생해야 함
        assertThatThrownBy(() -> parser.parse("//ab\n1ab2"))
            .isInstanceOf(DelimiterException.class)
            .hasMessageContaining("single character");
    }

    @Test
    @DisplayName("정수 오버플로우를 거부")
    void parseRejectsIntegerOverflow() {
        // Given: 정수 범위를 초과하는 값이 있는 문자열
        // When & Then: NumberParsingException이 발생해야 함
        assertThatThrownBy(() -> parser.parse("2147483648"))
            .isInstanceOf(NumberParsingException.class)
            .hasMessageContaining("exceeds integer range");
    }

    @Test
    @DisplayName("빈 문자열 입력 처리")
    void parseHandlesEmptyString() {
        // Given: 빈 문자열
        // When: 파싱을 수행
        List<Integer> numbers = parser.parse("");
        // Then: 빈 리스트가 반환되어야 함
        assertThat(numbers).isEmpty();
    }

    @Test
    @DisplayName("공백만 있는 입력 처리")
    void parseHandlesWhitespaceOnly() {
        // Given: 공백만 있는 문자열
        // When: 파싱을 수행
        List<Integer> numbers = parser.parse("   ");
        // Then: 빈 리스트가 반환되어야 함
        assertThat(numbers).isEmpty();
    }
}
