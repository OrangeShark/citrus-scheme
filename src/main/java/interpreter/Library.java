package interpreter;

import interpreter.Environment;

public abstract class Library {
    public abstract void importLib(Environment env);
}
