package interpreter;

public class SchemeString extends SchemeObject
{
    private char[] value;

    public SchemeString(char[] cs)
    {
        this.value = cs;
    }
}
