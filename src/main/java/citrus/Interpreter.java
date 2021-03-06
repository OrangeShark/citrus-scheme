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
import static citrus.util.List.*;

import java.io.Reader;
import java.io.IOException;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import scheme.antlr.SchemeLexer;
import scheme.antlr.SchemeParser;

public class Interpreter {

    private Environment globalEnv;
    private Unspecified unspecified;

    public Interpreter() {
        globalEnv = new Environment();
        unspecified = new Unspecified();
    }

    public SchemeObject read(String inputStr) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(inputStr);
        SchemeLexer lexer = new SchemeLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SchemeParser parser = new SchemeParser(tokens);
        ParseTree tree = parser.datum();

        SchemeReadVisitor visitor = new SchemeReadVisitor();
        return visitor.visit(tree);
    }

    public SchemeObject eval(SchemeObject obj, Environment env) {
        while(true) {
            if(obj instanceof Symbol) {
                return env.lookUp((Symbol)obj);
            } else if (obj instanceof Pair) {
                SchemeObject operator = eval(obj.car(), env);
                SchemeObject operands = obj.cdr();
                if(operator instanceof Applicable) {
                    Applicable op = (Applicable) operator;
                    SchemeList args  = evalList(operands, env);
                    obj = op.apply(args);
                    if(op instanceof Primitive) {
                        return obj;
                    }
                    env = (Environment)obj.cdr();
                    obj = obj.car();
                } else if(operator instanceof Syntax) {
                    Syntax op = (Syntax) operator;
                    switch(op.value) {
                    case LAMBDA:
                        return new Closure(env,
                                           SchemeList.of(operands.car()),
                                           operands.cdr());
                    case IF:
                        int count = length(operands);
                        if(count < 2 && count > 3) {
                            throw new SyntaxErrorException("Does not match if syntax");
                        }
                        SchemeObject predicate = eval(operands.car(), env);
                        if(Bool.isTruthy(predicate)) {
                            obj = second(operands);
                        } else if(count == 3){
                            obj = third(operands);
                        } else {
                            return unspecified;
                        }
                        break;
                    case COND:
                        obj = unspecified;
                        while(!operands.isNull() && obj == unspecified) {
                            SchemeObject clause = operands.car();
                            SchemeObject test = clause.car();
                            operands = operands.cdr();
                            if(test instanceof Symbol && ((Symbol)test).value.equals("else")
                               || Bool.isTruthy(eval(test, env))) {
                                obj = clause.cdr();
                            }
                        }

                        if(obj == unspecified)
                            return unspecified;
                        obj = new Pair(new Syntax(Syntax.Special.BEGIN), obj);
                        break;
                    case BEGIN:
                        if(operands.isNull()) {
                            return unspecified;
                        }
                        while(!operands.cdr().isNull()) {
                            eval(operands.car(), env);
                            operands = operands.cdr();
                        }
                        obj = operands.car();
                        break;
                    case DEFINE:
                        SchemeObject variable = operands.car();
                        if(variable instanceof Pair) {
                            obj = eval(new Pair(new Syntax(Syntax.Special.LAMBDA),
                                                new Pair(variable.cdr(),
                                                         operands.cdr())),
                                       env);
                            variable = variable.car();
                        } else {
                            if(length(operands) != 2)
                                throw new SyntaxErrorException("Does not match define syntax");
                            obj = eval(second(operands), env);
                        }
                        env.define(Symbol.of(variable), obj);
                        return unspecified;
                    case QUOTE:
                        if(length(operands) != 1)
                            throw new SyntaxErrorException("Does not match quote syntax");
                        return operands.car();
                    case SET:
                        if(length(operands) != 2)
                            throw new SyntaxErrorException("Does not match set! syntax");
                        env.set(Symbol.of(operands.car()), eval(second(operands), env));
                        return unspecified;
                    default:
                        return unspecified;
                    }
                } else {
                    // unknown
                    return null;
                }
            } else {
                return obj;
            }
        }
    }

    public SchemeList evalList(SchemeObject list, Environment env) {
        SchemeList acc = new Null();
        while(!list.isNull()) {
            acc = new Pair(eval(list.car(), env), acc);
            list = list.cdr();
        }
        return reverse(acc);
    }
}
