package interpreter.type;

import java.lang.RuntimeException;

public abstract class SchemeList extends SchemeObject {
    public static SchemeList of(SchemeObject obj) {
        if(obj instanceof SchemeList) {
            return (SchemeList)obj;
        } else {
            throw new RuntimeException("not a SchemeList");
        }
    }
}
