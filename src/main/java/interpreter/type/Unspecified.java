package interpreter.type;

public class Unspecified extends SchemeObject {
    public boolean equals(Object obj) {
        return obj instanceof Unspecified;
    }
    public String toString() {
        return "";
    }
}
