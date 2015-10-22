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
package citrus;

import citrus.type.SchemeObject;
import citrus.type.SchemeList;
import citrus.type.Symbol;
import citrus.type.Null;

import java.util.Hashtable;

public class Environment extends SchemeObject {
    private Environment parent;
    private Hashtable<String, SchemeObject> bindings;

    public Environment() {
        this(null, new Null(), new Null());
    }

    public Environment(Environment parent, SchemeList params, SchemeList args) {
        this.parent = parent;
        this.bindings = new Hashtable<String, SchemeObject>();
        while(!params.isNull()) {
            Symbol symbol = Symbol.of(params.car());
            this.define(symbol, args.car());
            params = SchemeList.of(params.cdr());
            args = SchemeList.of(args.cdr());
        }
    }

    public void define(Symbol name, SchemeObject value) {
        this.bindings.put(name.value, value);
    }

    public void set(Symbol name, SchemeObject value) {
        if(!this.bindings.containsKey(name.value)) {
            if(parent != null) {
                parent.set(name, value);
            } else {
                throw new UnboundVariableException(name + " is not bound");
            }
        } else {
            this.bindings.put(name.value, value);
        }
    }

    public SchemeObject lookUp(Symbol name) {
        SchemeObject obj = this.bindings.get(name.value);
        if(obj == null) {
            if(parent != null) {
                obj = parent.lookUp(name);
            } else {
                throw new UnboundVariableException(name + " is not bound");
            }
        }
        return obj;
    }
}
