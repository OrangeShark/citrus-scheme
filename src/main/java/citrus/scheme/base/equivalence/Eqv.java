package citrus.scheme.base.equivalence;

import citrus.type.*;
import static citrus.util.List.*;

import java.lang.IllegalArgumentException;

public class Eqv extends Primitive {
    public SchemeObject apply(SchemeList args) {
        boolean result = false;
        if(length(args) != 2) {
            throw new IllegalArgumentException("takes only two arguments");
        }
        final SchemeObject obj1 = first(args);
        final SchemeObject obj2 = second(args);

        if(obj1 instanceof Bool && obj2 instanceof Bool) {
            result = ((Bool)obj1).value == ((Bool)obj2).value;
        } else if(obj1 instanceof Symbol && obj2 instanceof Symbol) {
            result = ((Symbol)obj1).value == ((Symbol)obj2).value;
        } else if(obj1 instanceof Num && obj2 instanceof Num) {
            result = ((Num)obj1).value == ((Num)obj2).value;
        } else if(obj1 instanceof Char && obj2 instanceof Char) {
            result = ((Char)obj1).value == ((Char)obj2).value;
        } else {
            result = obj1 == obj2;
        }
        return new Bool(result);
    }
}
