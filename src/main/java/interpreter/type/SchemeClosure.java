package interpreter.type;

import interpreter.Environment;
import interpreter.Interpreter;

public class SchemeClosure extends SchemeObject implements Applicable {
    private Environment env;
    private SchemePair params;
    private SchemeObject body;

    public SchemeClosure(Environment env, SchemeObject params, SchemeObject body) {
        this.env = env;
        if(params instanceof SchemePair) {
            this.params = (SchemePair) params;
        } else {
            //error
            this.params = null;
        }
        this.body = new SchemePair(new Syntax(Syntax.Special.BEGIN), body);
    }

    public SchemeObject apply(Interpreter interpreter, SchemePair args) {
        Environment newEnv = new Environment(env, params, args);
        return interpreter.eval(body, newEnv);
    }
}
