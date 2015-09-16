package interpreter;

import scheme.antlr.SchemeBaseVisitor;
import scheme.antlr.SchemeParser;

public class SchemeReadVisitor extends SchemeBaseVisitor<SchemeObject>
{
    public SchemeObject visitBoolean(SchemeParser.BooleanContext ctx)
    {
        final String text = ctx.BOOLEAN().getText();
        if (text.equals("#f") || text.equals("#false")) {
            return new SchemeBoolean(false);
        } else {
            return new SchemeBoolean(true);
        }
    }

    public SchemeObject visitNumber(SchemeParser.NumberContext ctx)
    {
        return new SchemeNumber(Double.valueOf(ctx.NUMBER().getText()));
    }

    public SchemeObject visitCharacter(SchemeParser.CharacterContext ctx)
    {
        return null;
    }

    public SchemeObject visitString(SchemeParser.StringContext ctx)
    {
        return null;
    }

    public SchemeObject visitIdentifier(SchemeParser.IdentifierContext ctx)
    {
        return null;
    }

    public SchemeObject visitList(SchemeParser.ListContext ctx)
    {
        return null;
    }

    public SchemeObject visitVector(SchemeParser.VectorContext ctx)
    {
        return null;
    }

    public SchemeObject visitByteVector(SchemeParser.ByteVectorContext ctx)
    {
        return null;
    }
}
