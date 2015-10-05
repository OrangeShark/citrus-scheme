package interpreter.type;

public class SchemeNumber extends SchemeObject {
    public double value;

    public SchemeNumber(double n) {
        this.value = n;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
