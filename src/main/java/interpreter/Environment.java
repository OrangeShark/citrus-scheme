package interpreter;

import java.util.Hashtable;

public class Environment
{
    private Environment parent;
    private Hashtable<String, SchemeObject> bindings;

    public Environment()
    {
        this(null, null, null);
    }

    public Environment(Environment parent, SchemePair params, SchemePair args)
    {
        this.parent = parent;
        this.bindings = new Hashtable<String, SchemeObject>();
    }

    public void define(String name, SchemeObject value)
    {
        this.bindings.put(name, value);
    }

    public void set(String name, SchemeObject value)
    {
        if(!this.bindings.containsKey(name)) {
            // error, binding not found
        } else {
            this.bindings.put(name, value);
        }
    }
}
