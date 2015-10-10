package interpreter.type;

import java.lang.RuntimeException;

public class Symbol extends SchemeObject {
    public String value;

    public Symbol(String x) {
        this.value = x;
    }

    public static Symbol of(SchemeObject obj) {
        if(obj instanceof Symbol) {
            return (Symbol)obj;
        } else {
            throw new RuntimeException("not a symbol");
        }
    }

    public String toString() {
        return value;
    }
}
