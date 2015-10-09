package interpreter.type;

public class Str extends SchemeObject {
    private char[] value;

    public Str(char[] cs) {
        this.value = cs;
    }

    public Str(String str) {
        this.value = str.toCharArray();
    }

    public String toString() {
        return "\"" + String.valueOf(value) + "\"";
    }
}
