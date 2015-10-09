package interpreter.type;

public class Pair extends SchemeObject {
    public SchemeObject head;
    public SchemeObject tail;

    public Pair(SchemeObject head, SchemeObject tail) {
        this.head = head;
        this.tail = tail;
    }

    public Pair(SchemeObject head) {
        this(head, null);
    }

    public static Pair of(SchemeObject obj) {
        if(obj instanceof Pair || obj == null) {
            return (Pair) obj;
        } else {
            // error
            throw new RuntimeException("Not a pair");
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
        if(this.tail == null || this.tail instanceof Pair) {
            Pair element = (Pair)this.tail;
            while(element != null) {
                str += " " + element.head.toString();
                element = (Pair)element.tail;
            }
        } else {
            str += " . " + this.tail.toString();
        }
        return str + ")";
    }
}
