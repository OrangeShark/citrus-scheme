package interpreter.scheme;

import interpreter.*;
import interpreter.type.*;

import java.lang.IllegalArgumentException;

public class Base extends Library {
    public class Plus extends Primitive {
        public SchemeObject apply(Interpreter interpreter, SchemePair args) {
            double total = 0.0;
            while(args != null) {
                SchemeObject element = args.head;
                if(element instanceof SchemeNumber) {
                    total += ((SchemeNumber)element).value;
                } else {
                    throw new IllegalArgumentException("argument is not a Number");
                }
                args = SchemePair.of(args.tail);
            }
            return new SchemeNumber(total);
        }
    }

    public class Minus extends Primitive {
        public SchemeObject apply(Interpreter interpreter, SchemePair args) {
            return null;
        }
    }


    public void importLib(Environment env) {
        env.define("+", new Plus());

        // Special forms
        env.define("if", new Syntax(Syntax.Special.IF));
        env.define("lambda", new Syntax(Syntax.Special.LAMBDA));
        env.define("begin", new Syntax(Syntax.Special.BEGIN));
        env.define("define", new Syntax(Syntax.Special.DEFINE));
        env.define("quote", new Syntax(Syntax.Special.QUOTE));
        env.define("set!", new Syntax(Syntax.Special.SET));
    }
}
