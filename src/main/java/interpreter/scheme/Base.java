package interpreter.scheme;

import interpreter.*;
import interpreter.type.*;

import java.lang.IllegalArgumentException;

public class Base extends Library {
    public class Plus extends Primitive {
        public SchemeObject apply(Interpreter interpreter, Pair args) {
            double total = 0.0;
            while(args != null) {
                SchemeObject element = args.head;
                if(element instanceof Num) {
                    total += ((Num)element).value;
                } else {
                    throw new IllegalArgumentException("argument is not a Number");
                }
                args = Pair.of(args.tail);
            }
            return new Num(total);
        }
    }

    public class Minus extends Primitive {
        public SchemeObject apply(Interpreter interpreter, Pair args) {
            if(args == null) {
                throw new IllegalArgumentException("not enough arguments");
            }
            if(!(args.head instanceof Num)) {
                throw new IllegalArgumentException("argument is not a Number");
            }
            double total = ((Num)args.head).value;
            if(args.tail == null) {
                return new Num(-total);
            }
            args = Pair.of(args.tail);
            while(args != null) {
                SchemeObject element = args.head;
                if(!(element instanceof Num)) {
                    throw new IllegalArgumentException("argument is not a Number");
                }
                total -= ((Num)element).value;
                args = Pair.of(args.tail);
            }
            return new Num(total);
        }
    }

    public class Times extends Primitive {
        public SchemeObject apply(Interpreter interpreter, Pair args) {
            double total = 1.0;
            while(args != null) {
                SchemeObject element = args.head;
                if(element instanceof Num) {
                    total *= ((Num)element).value;
                } else {
                    throw new IllegalArgumentException("argument is not a Number");
                }
                args = Pair.of(args.tail);
            }
            return new Num(total);
        }
    }

    public class Divide extends Primitive {
        public SchemeObject apply(Interpreter interpreter, Pair args) {
            if(args == null) {
                throw new IllegalArgumentException("not enough arguments");
            }
            if(!(args.head instanceof Num)) {
                throw new IllegalArgumentException("argument is not a Number");
            }
            double total = ((Num)args.head).value;
            if(args.tail == null) {
                return new Num(1 / total);
            }
            args = Pair.of(args.tail);
            while(args != null) {
                SchemeObject element = args.head;
                if(!(element instanceof Num)) {
                    throw new IllegalArgumentException("argument is not a Number");
                }
                total /= ((Num)element).value;
                args = Pair.of(args.tail);
            }
            return new Num(total);
        }
    }

    public class Equality extends Primitive {
        public SchemeObject apply(Interpreter interpreter, Pair args) {
            boolean result = true;
            Num num = null;
            while(args != null) {
                SchemeObject element = args.head;
                if(!(element instanceof Num)) {
                    throw new IllegalArgumentException("argument is not a Number");
                }
                if(num == null) {
                    num = ((Num)element);
                } else {
                    result = result && num.value == ((Num)element).value;
                }
                args = Pair.of(args.tail);
            }
            return new Bool(result);
        }
    }


    public void importLib(Environment env) {
        env.define("+", new Plus());
        env.define("-", new Minus());
        env.define("*", new Times());
        env.define("/", new Divide());
        env.define("=", new Equality());

        // Special forms
        env.define("if", new Syntax(Syntax.Special.IF));
        env.define("lambda", new Syntax(Syntax.Special.LAMBDA));
        env.define("begin", new Syntax(Syntax.Special.BEGIN));
        env.define("define", new Syntax(Syntax.Special.DEFINE));
        env.define("quote", new Syntax(Syntax.Special.QUOTE));
        env.define("set!", new Syntax(Syntax.Special.SET));
    }
}
