package interpreter;

public class SchemeClosure extends SchemeObject implements Applicable {
    private Environment env;
    private SchemePair params;
    private SchemeObject body;

    public SchemeClosure(Environment env, SchemePair params, SchemeObject body) {
        this.env = env;
        this.params = params;
        this.body = body;
    }

    public SchemeObject apply(Interpreter interpreter, SchemePair args) {
        Environment newEnv = new Environment(env, params, args);
        return interpreter.eval(body, newEnv);
    }
}
