package interpreter;

public class SchemePair extends SchemeObject {
    private SchemeObject head;
    private SchemeObject tail;

    public SchemePair(SchemeObject head, SchemeObject tail)
    {
        this.head = head;
        this.tail = tail;
    }
}
