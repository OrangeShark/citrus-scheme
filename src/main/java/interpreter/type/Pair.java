package interpreter.type;

public class Pair extends SchemeList {
    public SchemeObject head;
    public SchemeObject tail;

    public Pair(SchemeObject head, SchemeObject tail) {
        this.head = head;
        this.tail = tail;
    }

    public Pair(SchemeObject head) {
        this(head, new Null());
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

    public boolean equals(Object obj) {
        Pair p = (Pair)obj;
        return this.head.equals(p.head)? this.tail.equals(p.tail) : false;
    }

    public String toString() {
        String str = "(" + this.head.toString();
        if(this.tail instanceof SchemeList) {
            SchemeList element = (SchemeList)this.tail;
            while(element instanceof Pair) {
                Pair p = (Pair)element;
                str += " " + p.head.toString();
                element = (SchemeList)p.tail;
            }
        } else if(!(this.tail instanceof Null)){
            str += " . " + this.tail.toString();
        }
        return str + ")";
    }
}
