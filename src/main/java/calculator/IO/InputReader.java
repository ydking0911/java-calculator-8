package calculator.IO;

import calculator.Exception.InputValidationException;
import camp.nextstep.edu.missionutils.Console;

public final class InputReader {
    public String read() {
        String line = Console.readLine();
        if (line == null) {
            throw InputValidationException.nullInput();
        }
        return line;
    }
}
