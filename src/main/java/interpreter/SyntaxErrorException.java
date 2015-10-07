package interpreter;

import java.lang.RuntimeException;

public class SyntaxErrorException extends RuntimeException {
    public SyntaxErrorException(String message) {
        super(message);
    }
}
