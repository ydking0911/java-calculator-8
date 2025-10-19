package calculator;

import calculator.IO.InputReader;
import calculator.IO.OutputWriter;

public class Application {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        StringParser parser = new StringParser();
        Calculator calculator = new Calculator();
        OutputWriter outputWriter = new OutputWriter();

        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input = inputReader.read();

        int result = calculator.sum(parser.parse(input));
        outputWriter.printResult(result);
    }
}
