package interpreter;

public class SchemePair extends SchemeObject {
    public SchemeObject head;
    public SchemeObject tail;

    public SchemePair(SchemeObject head, SchemeObject tail) {
        this.head = head;
        this.tail = tail;
    }

    public SchemePair(SchemeObject head) {
        this(head, null);
    }

    public static SchemePair is(SchemeObject obj) {
        if(obj instanceof SchemePair) {
            return (SchemePair) obj;
        } else {
            // error
            return null;
        }
    }

    public static SchemeObject car(SchemeObject obj) {
        if(obj != null && obj instanceof SchemePair) {
            return ((SchemePair)obj).head;
        } else {
            // error
            return null;
        }
    }

    public static SchemeObject cdr(SchemeObject obj) {
        if(obj != null && obj instanceof SchemePair) {
            return ((SchemePair)obj).tail;
        } else {
            // error
            return null;
        }
    }

    public String toString() {
        String str = "(" + this.head.toString();
        if(this.tail == null || this.tail instanceof SchemePair) {
            SchemePair element = (SchemePair)this.tail;
            while(element != null) {
                str += " " + element.head.toString();
                element = (SchemePair)element.tail;
            }
        } else {
            str += " . " + this.tail.toString();
        }
        return str + ")";
    }
}
