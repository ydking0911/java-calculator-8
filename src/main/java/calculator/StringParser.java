package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import calculator.Exception.DelimiterException;
import calculator.Exception.InputValidationException;
import calculator.Exception.NumberParsingException;

public final class StringParser {
    // 양의 정수 패턴 (1로 시작하는 숫자)
    private static final Pattern POSITIVE_INT = Pattern.compile("^[1-9]\\d*$");
    // 기본 구분자 패턴 (쉼표, 콜론)
    private static final Pattern DEFAULT_SPLIT = Pattern.compile("[,:]");

    public List<Integer> parse(String input) {
        // null 입력 검증
        if (input == null) {
            throw InputValidationException.nullInput();
        }
        String trimmed = input.trim();

        // 빈 문자열인 경우 빈 리스트 반환 (합은 0)
        if (trimmed.isEmpty()) {
            return List.of();
        }

        // 커스텀 구분자 처리
        if (trimmed.startsWith("//")) {
            int nl = trimmed.indexOf('\n');
            int headerEnd = nl;
            int bodyStart = nl + 1;
            
            // 실제 줄바꿈이 없는 경우 리터럴 \n 검색
            if (nl < 0) {
                int literalNewline = trimmed.indexOf("\\n");
                if (literalNewline < 0) {
                    throw DelimiterException.invalidFormat();
                }
                headerEnd = literalNewline;
                bodyStart = literalNewline + 2;
            }
            
            // 구분자 헤더 추출 및 검증
            String header = trimmed.substring(2, headerEnd);
            if (header.length() != 1) {
                throw DelimiterException.invalidLength();
            }
            
            // 구분자 뒤의 본문 추출 및 검증
            String body = trimmed.substring(bodyStart);
            if (body.isEmpty()) {
                throw InputValidationException.emptyInput();
            }
            
            // 커스텀 구분자와 기본 구분자를 모두 사용하여 분리
            String customDelimiter = Pattern.quote(header);
            String allDelimiters = customDelimiter + "|,|:";
            String[] tokens = body.split(allDelimiters, -1); // -1: 빈 토큰 검출
            
            return toPositiveIntegers(tokens);
        }

        // 기본 구분자(쉼표, 콜론)로 분리
        String[] tokens = DEFAULT_SPLIT.split(trimmed, -1);
        return toPositiveIntegers(tokens);
    }

    private List<Integer> toPositiveIntegers(String[] tokens) {
        List<Integer> numbers = new ArrayList<>(tokens.length);
        for (String raw : tokens) {
            String t = raw.trim();
            
            // 빈 토큰 검증
            if (t.isEmpty()) {
                throw InputValidationException.emptyToken();
            }
            
            // 양의 정수 패턴 검증
            if (!POSITIVE_INT.matcher(t).matches()) {
                throw NumberParsingException.nonPositiveValue(t);
            }
            
            // 정수 변환 및 오버플로우 검증
            try {
                numbers.add(Integer.parseInt(t));
            } catch (NumberFormatException e) {
                throw NumberParsingException.integerOverflow(t);
            }
        }
        return numbers;
    }
}
