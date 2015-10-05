package interpreter.type;

import interpreter.Interpreter;

public interface Applicable {
    public SchemeObject apply(Interpreter interpreter, SchemePair operands);
}
