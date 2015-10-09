package interpreter.util;

import interpreter.type.SchemeObject;
import interpreter.type.SchemeList;
import interpreter.type.Null;
import interpreter.type.Pair;

public class List {
    public static SchemeObject second(SchemeObject list) {
        return list.cdr().car();
    }

    public static SchemeObject third(SchemeObject list) {
        return list.cdr().cdr().car();
    }

    public static SchemeList reverse(SchemeList list) {
        SchemeList prevTail = new Null();
        while(!list.isNull()) {
            Pair p = Pair.of(list);
            SchemeList tmp = SchemeList.of(p.tail);
            p.tail = prevTail;
            prevTail = list;
            list = tmp;
        }
        return prevTail;
    }

    public static int length(SchemeObject list) {
        int count = 0;
        while(!list.isNull()) {
            list = list.cdr();
            count++;
        }
        return count;
    }
}
