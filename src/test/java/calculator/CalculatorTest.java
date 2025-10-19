package calculator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    @DisplayName("양의 정수들의 합 계산")
    void sumPositiveIntegers() {
        // Given: 양의 정수 리스트
        // When: 합을 계산
        int result = calculator.sum(List.of(1, 2, 3, 4));
        // Then: 정확한 합이 반환되어야 함
        assertThat(result).isEqualTo(10);
    }

    @Test
    @DisplayName("빈 리스트의 합은 0")
    void sumEmptyList() {
        // Given: 빈 정수 리스트
        // When: 합을 계산
        int result = calculator.sum(List.of());
        // Then: 0이 반환되어야 함
        assertThat(result).isZero();
    }
}
