package calculator;

import java.util.List;

public final class Calculator {
    public int sum(List<Integer> numbers) {
        int result = 0;
        for (int n : numbers) {
            result += n;
        }
        return result;
    }
}