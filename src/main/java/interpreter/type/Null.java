package interpreter.type;

public class Null extends SchemeList {
    public Null() {}

    public boolean isNull() {
        return true;
    }

    public String toString() {
        return "()";
    }
}
