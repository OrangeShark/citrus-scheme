package interpreter.type;

import interpreter.Environment;
import interpreter.Interpreter;

public class Closure extends SchemeObject implements Applicable {
    private Environment env;
    private SchemeList params;
    private SchemeObject body;

    public Closure(Environment env, SchemeList params, SchemeObject body) {
        this.env = env;
        this.params = params;
        this.body = new Pair(new Syntax(Syntax.Special.BEGIN), body);
    }

    public SchemeObject apply(Interpreter interpreter, SchemeList args) {
        Environment newEnv = new Environment(env, params, args);
        return interpreter.eval(body, newEnv);
    }
}
