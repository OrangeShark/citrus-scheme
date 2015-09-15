package interpreter;

public class SchemeClosure extends SchemeObject
{
    private Environment env;
    private SchemePair params;
    private SchemeObject body;

    public SchemeClosure(Environment env, SchemePair params, SchemeObject body)
    {
        this.env = env;
        this.params = params;
        this.body = body;
    }
}
