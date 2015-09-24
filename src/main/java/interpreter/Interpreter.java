package interpreter;

import java.io.InputStream;
import java.io.IOException;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import scheme.antlr.SchemeLexer;
import scheme.antlr.SchemeParser;

public class Interpreter {

    private Environment globalEnv;

    public Interpreter() {
        globalEnv = new Environment();
    }

    public SchemeObject read(InputStream is) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(is);
        SchemeLexer lexer = new SchemeLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SchemeParser parser = new SchemeParser(tokens);
        ParseTree tree = parser.datum();

        SchemeReadVisitor visitor = new SchemeReadVisitor();
        return visitor.visit(tree);
    }
}
