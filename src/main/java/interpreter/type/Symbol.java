package interpreter.type;

public class Symbol extends SchemeObject {
    private String value;

    public Symbol(String x) {
        this.value = x;
    }

    public String toString() {
        return value;
    }
}
