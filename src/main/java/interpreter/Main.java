package interpreter;

public class Main {

    public static void main(String[] args) throws Exception {
        Interpreter interpreter = new Interpreter();
        System.out.print("> ");
        SchemeObject obj = interpreter.read(System.in);
        System.out.println(obj);
    }
}
