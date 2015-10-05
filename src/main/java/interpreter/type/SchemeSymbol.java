package interpreter.type;

public class SchemeSymbol extends SchemeObject {
    private String value;

    public SchemeSymbol(String x) {
        this.value = x;
    }

    public String toString() {
        return value;
    }
}
