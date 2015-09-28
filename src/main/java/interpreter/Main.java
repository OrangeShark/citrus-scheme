package interpreter;

import interpreter.scheme.Base;

public class Main {

    public static void main(String[] args) throws Exception {
        Interpreter interpreter = new Interpreter();
        System.out.print("> ");
        SchemeObject obj = interpreter.read(System.in);
        Environment env = new Environment();
        Library lib = new Base();
        lib.importLib(env);
        SchemeObject result = interpreter.eval(obj, env);
        System.out.println(result);
    }
}

