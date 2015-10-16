package interpreter;

import interpreter.type.SchemeObject;
import interpreter.type.SchemeList;
import interpreter.type.Symbol;
import interpreter.type.Null;

import java.util.Hashtable;

public class Environment {
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
