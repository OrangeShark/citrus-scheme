package interpreter.util;

import interpreter.type.SchemeObject;
import interpreter.type.SchemePair;

public class List {
    public static SchemeObject second(SchemeObject list) {
        return list.cdr().car();
    }

    public static SchemeObject third(SchemeObject list) {
        return list.cdr().cdr().car();
    }

    public static SchemePair reverse(SchemePair list) {
        SchemePair prevTail = null;
        while(list != null) {
            SchemePair tmp = SchemePair.of(list.tail);
            list.tail = prevTail;
            prevTail = list;
            list = tmp;
        }
        return prevTail;
    }

    public static int length(SchemeObject list) {
        int count = 0;
        while(list != null) {
            list = list.cdr();
            count++;
        }
        return count;
    }
}
