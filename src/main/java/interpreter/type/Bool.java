package interpreter.type;

public class Bool extends SchemeObject {
    public boolean value;

    public Bool(boolean b) {
        this.value = b;
    }

    public String toString() {
        return value ? "#t" : "#f";
    }

    public static boolean isTruthy(SchemeObject obj) {
        if(obj instanceof Bool) {
            return ((Bool)obj).value;
        }
        return true;
    }
}
