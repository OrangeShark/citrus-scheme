package interpreter;

public class SchemeVector extends SchemeObject
{
    private SchemeObject[] value;

    public SchemeVector(SchemeObject[] xs)
    {
        this.value = xs;
    }
}
