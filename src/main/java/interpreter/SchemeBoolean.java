package interpreter;

public class SchemeBoolean extends SchemeObject {
    public boolean value;

    public SchemeBoolean(boolean b) {
        this.value = b;
    }

    public String toString() {
        return value ? "#t" : "#f";
    }

    public static boolean isTruthy(SchemeObject obj) {
        if(obj instanceof SchemeBoolean) {
            return ((SchemeBoolean)obj).value;
        }
        return true;
    }
}
