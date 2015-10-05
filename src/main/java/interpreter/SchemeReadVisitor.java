package interpreter;

import interpreter.type.*;

import java.util.List;

import scheme.antlr.SchemeBaseVisitor;
import scheme.antlr.SchemeParser;

public class SchemeReadVisitor extends SchemeBaseVisitor<SchemeObject>
{
    public SchemeObject visitTrue(SchemeParser.TrueContext ctx)
    {
        return new SchemeBoolean(true);
    }

    public SchemeObject visitFalse(SchemeParser.FalseContext ctx)
    {
        return new SchemeBoolean(false);
    }

    public SchemeObject visitNum10(SchemeParser.Num10Context ctx)
    {
        return new SchemeNumber(Double.valueOf(ctx.NUM_10().getText()));
    }

    public SchemeObject visitCharacter(SchemeParser.CharacterContext ctx)
    {
        final String text = ctx.CHARACTER().getText().substring(2);
        switch(text) {
        case "alarm":
            return null;
        case "backspace":
            return null;
        case "delete":
            return null;
        case "escape":
            return null;
        case "newline":
            return new SchemeCharacter('\n');
        case "null":
            return new SchemeCharacter('\0');
        case "return":
            return new SchemeCharacter('\r');
        case "space":
            return new SchemeCharacter(' ');
        case "tab":
            return new SchemeCharacter('\t');
        default:
            if(text.charAt(0) == 'x')
                // hex scalar value
                return null;
            else
                return new SchemeCharacter(text.charAt(0));
        }
    }

    public SchemeObject visitString(SchemeParser.StringContext ctx)
    {
        final String str = ctx.STRING().getText();
        return new SchemeString(str.substring(1, str.length() - 1));
    }

    public SchemeObject visitIdentifier(SchemeParser.IdentifierContext ctx)
    {
        return new SchemeSymbol(ctx.IDENTIFIER().getText());
    }

    public SchemeObject visitList(SchemeParser.ListContext ctx)
    {
        final List<SchemeParser.DatumContext> elements = ctx.datum();

        return buildList(elements);
    }

    public SchemeObject visitVector(SchemeParser.VectorContext ctx)
    {
        final List<SchemeParser.DatumContext> elements = ctx.datum();
        return new SchemePair(new SchemeSymbol("vector"),
                              buildList(elements));
    }

    public SchemeObject visitByteVector(SchemeParser.ByteVectorContext ctx)
    {
        return null;
    }

    private SchemePair buildList(List<SchemeParser.DatumContext> elements)
    {
        final SchemePair head = new SchemePair(visit(elements.get(0)));
        SchemePair pair = head;
        for(int i = 1; i < elements.size(); i++) {
            SchemePair tail = new SchemePair(visit(elements.get(i)));
            pair.tail = tail;
            pair = tail;
        }
        return head;
    }
}
