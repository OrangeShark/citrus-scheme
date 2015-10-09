package interpreter;

import interpreter.type.SchemeObject;
import interpreter.type.SchemeList;

import java.util.Hashtable;

public class Environment {
    private Environment parent;
    private Hashtable<String, SchemeObject> bindings;

    public Environment() {
        this(null, null, null);
    }

    public Environment(Environment parent, SchemeList params, SchemeList args) {
        this.parent = parent;
        this.bindings = new Hashtable<String, SchemeObject>();
        while(params != null) {
            this.define(params.car().toString(), args.car());
            params = SchemeList.of(params.cdr());
            args = SchemeList.of(args.cdr());
        }
    }

    public void define(String name, SchemeObject value) {
        this.bindings.put(name, value);
    }

    public void set(String name, SchemeObject value) {
        if(!this.bindings.containsKey(name)) {
            if(parent != null) {
                parent.set(name, value);
            } else {
                throw new UnboundVariableException(name + " is not bound");
            }
        } else {
            this.bindings.put(name, value);
        }
    }

    public SchemeObject lookUp(String name) {
        SchemeObject obj = this.bindings.get(name);
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
