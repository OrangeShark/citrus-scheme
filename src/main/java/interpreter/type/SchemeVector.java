package interpreter.type;

public class SchemeVector extends SchemeObject {
    private SchemeObject[] value;

    public SchemeVector(SchemeObject[] xs) {
        this.value = xs;
    }
}
