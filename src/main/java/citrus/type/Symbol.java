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
package citrus.type;

import java.lang.RuntimeException;

public class Symbol extends SchemeObject {
    public final String value;

    public Symbol(String x) {
        this.value = x;
    }

    public static Symbol of(SchemeObject obj) {
        if(obj instanceof Symbol) {
            return (Symbol)obj;
        } else {
            throw new RuntimeException("not a symbol");
        }
    }

    public String toString() {
        return value;
    }
}
