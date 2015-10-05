package interpreter;

import interpreter.type.*;

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

    public SchemeObject eval(SchemeObject obj, Environment env) {
        if(obj instanceof SchemeSymbol) {
            return env.lookUp(obj.toString());
        } else if (obj instanceof SchemePair) {
            SchemeObject operator = eval(obj.car(), env);
            SchemeObject operands = obj.cdr();
            if(operator instanceof Applicable) {
                Applicable op = (Applicable) operator;
                SchemePair args  = evalList(operands, env);
                return op.apply(this, args);
            } else if(operator instanceof Syntax) {
                Syntax op = (Syntax) operator;
                switch(op.value) {
                    case LAMBDA:
                        return new SchemeClosure(env, operands.car(),
                                                 operands.cdr());
                    case IF:
                        SchemeObject predicate = eval(operands.car(), env);
                        if(SchemeBoolean.isTruthy(predicate)) {
                            return eval(second(operands), env);
                        } else {
                            return eval(third(operands), env);
                        }
                    case BEGIN:
                        SchemeObject result = null;
                        while(operands != null) {
                            result = eval(operands.car(), env);
                            operands = operands.cdr();
                        }
                        return result;
                    case DEFINE:
                        env.define(operands.car().toString(),
                                   eval(second(operands), env));
                        return null;
                    default:
                        return null;
                }
            } else {
                // unknown
                return null;
            }
        } else {
            return obj;
        }
    }

    public SchemePair evalList(SchemeObject list, Environment env) {
        SchemePair acc = null;
        while(list != null) {
            acc = new SchemePair(eval(list.car(), env), acc);
            list = list.cdr();
        }
        return reverseList(acc);
    }

    private SchemePair reverseList(SchemePair list) {
        SchemePair prevTail = null;
        while(list != null) {
            SchemePair tmp = SchemePair.is(list.tail);
            list.tail = prevTail;
            prevTail = list;
            list = tmp;
        }
        return prevTail;
    }

    private SchemeObject second(SchemeObject list) {
        return list.cdr().car();
    }

    private SchemeObject third(SchemeObject list) {
        return list.cdr().cdr().car();
    }
}
