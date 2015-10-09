package interpreter.type;

import java.lang.IllegalArgumentException;

public abstract class SchemeObject {

    public SchemeObject car() {
        throw new IllegalArgumentException(this.toString());
    }

    public SchemeObject cdr() {
        throw new IllegalArgumentException(this.toString());
    }

    public boolean isNull() {
        return false;
    }
}
