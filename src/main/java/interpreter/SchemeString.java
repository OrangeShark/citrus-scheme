package interpreter;

public class SchemeString extends SchemeObject {
    private char[] value;

    public SchemeString(char[] cs) {
        this.value = cs;
    }

    public SchemeString(String str) {
        this.value = str.toCharArray();
    }

    public String toString() {
        return "\"" + String.valueOf(value) + "\"";
    }
}
