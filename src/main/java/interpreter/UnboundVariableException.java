package interpreter;

import java.lang.RuntimeException;

public class UnboundVariableException extends RuntimeException {
    public UnboundVariableException(String message) {
        super(message);
    }
}
