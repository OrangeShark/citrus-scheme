package interpreter.type;

import interpreter.Environment;
import interpreter.Interpreter;

public class Closure extends SchemeObject implements Applicable {
    private Environment env;
    private Pair params;
    private SchemeObject body;

    public Closure(Environment env, SchemeObject params, SchemeObject body) {
        this.env = env;
        if(params instanceof Pair) {
            this.params = (Pair) params;
        } else {
            //error
            this.params = null;
        }
        this.body = new Pair(new Syntax(Syntax.Special.BEGIN), body);
    }

    public SchemeObject apply(Interpreter interpreter, Pair args) {
        Environment newEnv = new Environment(env, params, args);
        return interpreter.eval(body, newEnv);
    }
}
