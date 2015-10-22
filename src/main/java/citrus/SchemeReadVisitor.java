/*
 * Copyright 2015 Erik Edrosa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package citrus;

import citrus.type.*;

import java.util.List;

import scheme.antlr.SchemeBaseVisitor;
import scheme.antlr.SchemeParser;

public class SchemeReadVisitor extends SchemeBaseVisitor<SchemeObject>
{
    public SchemeObject visitTrue(SchemeParser.TrueContext ctx)
    {
        return new Bool(true);
    }

    public SchemeObject visitFalse(SchemeParser.FalseContext ctx)
    {
        return new Bool(false);
    }

    public SchemeObject visitNum10(SchemeParser.Num10Context ctx)
    {
        return new Num(Double.valueOf(ctx.NUM_10().getText()));
    }

    public SchemeObject visitCharacter(SchemeParser.CharacterContext ctx)
    {
        final String text = ctx.CHARACTER().getText().substring(2);
        switch(text) {
        case "alarm":
            return new Char('\u0007');
        case "backspace":
            return new Char('\u0008');
        case "delete":
            return new Char('\u007F');
        case "escape":
            return new Char('\u001B');
        case "newline":
            return new Char('\n');
        case "null":
            return new Char('\u0000');
        case "return":
            return new Char('\r');
        case "space":
            return new Char(' ');
        case "tab":
            return new Char('\t');
        default:
            if(text.charAt(0) == 'x')
                // hex scalar value
                return null;
            else
                return new Char(text.charAt(0));
        }
    }

    public SchemeObject visitString(SchemeParser.StringContext ctx)
    {
        final String str = ctx.STRING().getText();
        return new Str(str.substring(1, str.length() - 1));
    }

    public SchemeObject visitIdentifier(SchemeParser.IdentifierContext ctx)
    {
        return new Symbol(ctx.IDENTIFIER().getText());
    }

    public SchemeObject visitList(SchemeParser.ListContext ctx)
    {
        final List<SchemeParser.DatumContext> elements = ctx.datum();

        return elements.size() == 0 ? new Null() : buildList(elements);
    }

    public SchemeObject visitVector(SchemeParser.VectorContext ctx)
    {
        final List<SchemeParser.DatumContext> elements = ctx.datum();
        return new Pair(new Symbol("vector"),
                        buildList(elements));
    }

    public SchemeObject visitByteVector(SchemeParser.ByteVectorContext ctx)
    {
        return null;
    }

    public SchemeObject visitQuote(SchemeParser.QuoteContext ctx)
    {
        final SchemeObject obj = visit(ctx.datum());
        return new Pair(new Syntax(Syntax.Special.QUOTE), 
                        new Pair(obj));
    }

    private Pair buildList(List<SchemeParser.DatumContext> elements)
    {
        final Pair head = new Pair(visit(elements.get(0)));
        Pair pair = head;
        for(int i = 1; i < elements.size(); i++) {
            Pair tail = new Pair(visit(elements.get(i)));
            pair.tail = tail;
            pair = tail;
        }
        return head;
    }
}
