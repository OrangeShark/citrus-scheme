package interpreter.scheme;

import interpreter.*;
import interpreter.type.*;
import static interpreter.util.List.*;

import java.lang.IllegalArgumentException;

public class Base extends Library {
    public class Plus extends Primitive {
        public SchemeObject apply(Interpreter interpreter, SchemeList args) {
            double total = 0.0;
            while(!args.isNull()) {
                SchemeObject element = args.car();
                if(element instanceof Num) {
                    total += ((Num)element).value;
                } else {
                    throw new IllegalArgumentException("argument is not a Number");
                }
                args = SchemeList.of(args.cdr());
            }
            return new Num(total);
        }
    }

    public class Minus extends Primitive {
        public SchemeObject apply(Interpreter interpreter, SchemeList args) {
            if(args.isNull()) {
                throw new IllegalArgumentException("not enough arguments");
            }
            if(!(args.car() instanceof Num)) {
                throw new IllegalArgumentException("argument is not a Number");
            }
            double total = ((Num)args.car()).value;
            if(args.cdr().isNull()) {
                return new Num(-total);
            }
            args = SchemeList.of(args.cdr());
            while(!args.isNull()) {
                SchemeObject element = args.car();
                if(!(element instanceof Num)) {
                    throw new IllegalArgumentException("argument is not a Number");
                }
                total -= ((Num)element).value;
                args = SchemeList.of(args.cdr());
            }
            return new Num(total);
        }
    }

    public class Times extends Primitive {
        public SchemeObject apply(Interpreter interpreter, SchemeList args) {
            double total = 1.0;
            while(!args.isNull()) {
                SchemeObject element = args.car();
                if(element instanceof Num) {
                    total *= ((Num)element).value;
                } else {
                    throw new IllegalArgumentException("argument is not a Number");
                }
                args = SchemeList.of(args.cdr());
            }
            return new Num(total);
        }
    }

    public class Divide extends Primitive {
        public SchemeObject apply(Interpreter interpreter, SchemeList args) {
            if(args.isNull()) {
                throw new IllegalArgumentException("not enough arguments");
            }
            if(!(args.car() instanceof Num)) {
                throw new IllegalArgumentException("argument is not a Number");
            }
            double total = ((Num)args.car()).value;
            if(args.cdr().isNull()) {
                return new Num(1 / total);
            }
            args = SchemeList.of(args.cdr());
            while(!args.isNull()) {
                SchemeObject element = args.car();
                if(!(element instanceof Num)) {
                    throw new IllegalArgumentException("argument is not a Number");
                }
                total /= ((Num)element).value;
                args = SchemeList.of(args.cdr());
            }
            return new Num(total);
        }
    }

    public class Equality extends Primitive {
        public SchemeObject apply(Interpreter interpreter, SchemeList args) {
            boolean result = true;
            Num num = null;
            while(!args.isNull()) {
                SchemeObject element = args.car();
                if(!(element instanceof Num)) {
                    throw new IllegalArgumentException("argument is not a Number");
                }
                if(num == null) {
                    num = ((Num)element);
                } else {
                    result = result && num.value == ((Num)element).value;
                }
                args = SchemeList.of(args.cdr());
            }
            return new Bool(result);
        }
    }

    public class Car extends Primitive {
        public SchemeObject apply(Interpreter interpreter, SchemeList args) {
            if(length(args) != 1) {
                throw new IllegalArgumentException("wrong number of arguments");
            }
            return args.car().car();
        }
    }

    public class Cdr extends Primitive {
        public SchemeObject apply(Interpreter interpreter, SchemeList args) {
            if(length(args) != 1) {
                throw new IllegalArgumentException("wrong number of arguments");
            }
            return args.car().cdr();
        }
    }

    public class NullP extends Primitive {
        public SchemeObject apply(Interpreter interpreter, SchemeList args) {
            if(length(args) != 1) {
                throw new IllegalArgumentException("wrong number of arguments");
            }
            return new Bool(args.car().isNull());
        }
    }

    public void importLib(Environment env) {
        env.define(new Symbol("+"), new Plus());
        env.define(new Symbol("-"), new Minus());
        env.define(new Symbol("*"), new Times());
        env.define(new Symbol("/"), new Divide());
        env.define(new Symbol("="), new Equality());
        env.define(new Symbol("car"), new Car());
        env.define(new Symbol("cdr"), new Cdr());
        env.define(new Symbol("null?"), new NullP());

        // Special forms
        env.define(new Symbol("if"), new Syntax(Syntax.Special.IF));
        env.define(new Symbol("lambda"), new Syntax(Syntax.Special.LAMBDA));
        env.define(new Symbol("begin"), new Syntax(Syntax.Special.BEGIN));
        env.define(new Symbol("define"), new Syntax(Syntax.Special.DEFINE));
        env.define(new Symbol("quote"), new Syntax(Syntax.Special.QUOTE));
        env.define(new Symbol("set!"), new Syntax(Syntax.Special.SET));
    }
}
