package interpreter.type;

public class Num extends SchemeObject {
    public double value;

    public Num(double n) {
        this.value = n;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
