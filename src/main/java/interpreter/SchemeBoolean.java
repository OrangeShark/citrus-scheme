package interpreter;

public class SchemeBoolean extends SchemeObject {
    private boolean value;

    public SchemeBoolean(boolean b) {
        this.value = b;
    }

    public String toString() {
        return value ? "#t" : "#f";
    }
}
