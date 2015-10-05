package interpreter.type;

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

    public SchemeObject car() {
        return this.head;
    }

    public SchemeObject cdr() {
        return this.tail;
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
