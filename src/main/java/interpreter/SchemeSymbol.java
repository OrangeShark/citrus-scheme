package interpreter;

public class SchemeSymbol extends SchemeObject
{
    private String value;

    public SchemeSymbol(String x)
    {
        this.value = x;
    }
}