package interpreter.type;

public class Vec extends SchemeObject {
    private SchemeObject[] value;

    public Vec(SchemeObject[] xs) {
        this.value = xs;
    }
}
