package interpreter;

public class SchemePair extends SchemeObject {
    private SchemeObject head;
    private SchemeObject tail;

    public SchemePair(SchemeObject head, SchemeObject tail)
    {
        this.head = head;
        this.tail = tail;
    }

    public SchemePair(SchemeObject head)
    {
        this(head, null);
    }

    public void setHead(SchemeObject obj)
    {
        this.head = obj;
    }

    public void setTail(SchemeObject obj)
    {
        this.tail = obj;
    }

    public SchemeObject getHead()
    {
        return this.head;
    }

    public SchemeObject getTail()
    {
        return this.tail;
    }

    public String toString()
    {
        String str = "(" + this.getHead().toString();
        if(this.getTail() == null || this.getTail() instanceof SchemePair) {
            SchemePair element = (SchemePair)this.getTail();
            while(element != null) {
                str += " " + element.getHead().toString();
                element = (SchemePair)element.getTail();
            }
        } else {
            str += " . " + this.getTail().toString();
        }
        return str + ")";
    }
}
