package interpreter;

import interpreter.scheme.Base;
import interpreter.type.*;

import java.lang.StringBuilder;
import java.io.IOException;
import java.io.Reader;

public class Main {
    private static int BUFFER_SIZE = 1024;

    private static String readInput(Reader reader) throws IOException {
        char[] buffer = new char[BUFFER_SIZE];
        StringBuilder strBuilder = new StringBuilder();
        int numRead = 0;
        do {
            numRead = reader.read(buffer, 0, BUFFER_SIZE);
            strBuilder.append(buffer, 0, numRead);
        } while(numRead == BUFFER_SIZE);

        return strBuilder.toString();
    }

    public static void main(String[] args) throws Exception {
        Interpreter interpreter = new Interpreter();

        Environment env = new Environment();
        Library lib = new Base();
        SchemeInputReader reader = new SchemeInputReader(System.in);
        lib.importLib(env);
        while(true) {
            System.out.print("> ");
            String input = readInput(reader);
            SchemeObject obj = interpreter.read(input);
            SchemeObject result = interpreter.eval(obj, env);
            System.out.println(result);
            reader.reset();
        }
    }
}
