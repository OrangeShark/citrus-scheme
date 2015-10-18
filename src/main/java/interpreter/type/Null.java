package interpreter.type;

public class Null extends SchemeList {
    public Null() {}

    public boolean isNull() {
        return true;
    }

    public boolean equals(Object obj) {
        return obj instanceof Null? true : false;
    }

    public String toString() {
        return "()";
    }
}
