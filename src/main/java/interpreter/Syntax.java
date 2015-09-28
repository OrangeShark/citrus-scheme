package interpreter;

public class Syntax extends SchemeObject {
    public enum Special {
        LAMBDA,
        DEFINE,
        BEGIN,
        COND,
        IF,
        SET,
        QUOTE
    }

    public Special value;

    public Syntax(Special special) {
        this.value = special;
    }
}
