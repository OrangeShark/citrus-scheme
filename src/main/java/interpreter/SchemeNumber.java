package interpreter;

public class SchemeNumber extends SchemeObject {
    private double value;

    public SchemeNumber(double n) {
        this.value = n;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
