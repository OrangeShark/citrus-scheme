package interpreter;

public interface Applicable {
    public SchemeObject apply(Interpreter interpreter, SchemePair operands);
}
