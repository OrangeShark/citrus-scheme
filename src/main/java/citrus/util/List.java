/*
 * Copyright 2015 Erik Edrosa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package citrus.util;

import citrus.type.SchemeObject;
import citrus.type.SchemeList;
import citrus.type.Null;
import citrus.type.Pair;

public class List {
    public static SchemeList list(SchemeObject ... elements) {
        SchemeList l = new Null();
        for(int i = 0; i < elements.length; i++) {
            l = new Pair(elements[i], l);
        }
        return reverse(l);
    }

    public static SchemeObject first(SchemeObject list) {
        return list.car();
    }

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
