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

import citrus.Interpreter;
import citrus.Environment;
import citrus.SyntaxErrorException;
import citrus.type.*;
import citrus.type.TestObject;

import static citrus.util.List.*;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class InterpreterTest {

    private Environment env;
    private Interpreter interpreter;

    @Before
    public void setUp() {
        env = mock(Environment.class);
        interpreter = new Interpreter();
    }

    @Test
    public void evalWhenGivenSymbolLookUpIsCalledAndReturnsObject() {
        Symbol sym = new Symbol("test");
        TestObject obj = new TestObject();
        when(env.lookUp(sym)).thenReturn(obj);
        assertSame(obj, interpreter.eval(sym, env));
    }

    @Test
    public void evalWhenNotGivenPairOrSymbolObjectThenReturnsObject() {
        TestObject obj = new TestObject();
        assertSame(obj, interpreter.eval(obj, env));
    }

    @Test
    public void evalWhenGivenPairWithApplicableOpThenCallsApplyAndReturnsResult() {
        Primitive op = mock(Primitive.class);
        TestObject obj = new TestObject();
        Pair args = new Pair(new TestObject());
        Pair exp = new Pair(op, args);
        when(op.apply(args)).thenReturn(obj);
        assertSame(interpreter.eval(exp, env), obj);
    }

    @Test
    public void evalWhenGivenPairWithIfSpecialFormAndTrueExpressionThenReturnsSecondExpression() {
        Syntax form = new Syntax(Syntax.Special.IF);
        SchemeList operands = list(new Bool(true), new TestObject());
        SchemeList exp = new Pair(form, operands);
        assertSame(second(operands), interpreter.eval(exp, env));
    }

    @Test
    public void evalWhenGivenPairWithIfSpecialFormAndFalseExpressionWithNoElseReturnsUnspecified() {
        Syntax form = new Syntax(Syntax.Special.IF);
        SchemeList operands = list(new Bool(false), new TestObject());
        SchemeList exp = new Pair(form, operands);
        assertEquals(new Unspecified(), interpreter.eval(exp, env));
    }

    @Test
    public void evalWhenGivenPairWithIfSpecialFormAndFalseExpressionWithElseReturnsThirdExpression() {
        Syntax form = new Syntax(Syntax.Special.IF);
        SchemeList operands = list(new Bool(false), new TestObject(), new TestObject());
        SchemeList exp = new Pair(form, operands);
        assertSame(third(operands), interpreter.eval(exp, env));
    }

    @Test
    public void evalWhenGivenPairWithCondSpecialFormAndTrueClauseThenReturnsClauseExpression() {
        Syntax form = new Syntax(Syntax.Special.COND);
        SchemeList clause = list(new Bool(true), new TestObject());
        SchemeList exp = list(form, clause);
        assertSame(second(clause), interpreter.eval(exp, env));
    }

    @Test
    public void evalWhenGivenPairWithCondSpecialFormAndFalseClauseThenReturnsUnspecified() {
        Syntax form = new Syntax(Syntax.Special.COND);
        SchemeList clause = list(new Bool(false), new TestObject());
        SchemeList exp = list(form, clause);
        assertEquals(new Unspecified(), interpreter.eval(exp, env));
    }

    @Test
    public void evalWhenGivenExpWithCondSpecialFormAndAnElseClauseThenReturnsElseExpression() {
        Syntax form = new Syntax(Syntax.Special.COND);
        SchemeList clause = list(new Bool(false), new TestObject());
        SchemeList elseClause = list(new Symbol("else"), new TestObject());
        SchemeList exp = list(form, clause, elseClause);
        assertSame(second(elseClause), interpreter.eval(exp, env));
    }

    @Test
    public void evalWhenLastAndOnlyExpressionInBeginSpecialFormReturnsExpression() {
        Syntax form = new Syntax(Syntax.Special.BEGIN);
        SchemeList exp = list(form, new TestObject());
        assertSame(second(exp), interpreter.eval(exp, env));
    }

    @Test
    public void evalWhenLastExpressionInBeginSpecialFormReturnsExpression() {
        Syntax form = new Syntax(Syntax.Special.BEGIN);
        SchemeList exp = list(form, new TestObject(), new TestObject());
        assertSame(third(exp), interpreter.eval(exp, env));
    }

    @Test
    public void evalWhenExpressionWithDefineSpecialFormThenCallsDefineOnEnv() {
        Syntax form = new Syntax(Syntax.Special.DEFINE);
        Symbol symbol = new Symbol("Test");
        TestObject obj = new TestObject();
        SchemeList exp = list(form, symbol, obj);
        assertEquals(new Unspecified(), interpreter.eval(exp, env));
        verify(env).define(symbol, obj);
    }

    @Test(expected= SyntaxErrorException.class)
    public void evalWhenMoreThanOneExpressionWithDefineSpecialFormThenError() {
        Syntax form = new Syntax(Syntax.Special.DEFINE);
        SchemeList exp = list(form, new Symbol("Test"), new TestObject(), new TestObject());
        interpreter.eval(exp, env);
    }

    @Test
    public void evalWhenGivenFunctionWithDefineSpecialFormThenDefineOnEnvALambda() {
        Syntax form = new Syntax(Syntax.Special.DEFINE);
        SchemeList exp = list(form, list(new Symbol("Test")), new TestObject());
        assertEquals(new Unspecified(), interpreter.eval(exp, env));
        verify(env).define(eq((Symbol)second(exp).car()), any(Closure.class));
    }

    @Test(expected= SyntaxErrorException.class)
    public void evalWhenGivenMoreThanOneExpressionWithQuoteSpecialFormThenSyntaxError() {
        Syntax form = new Syntax(Syntax.Special.QUOTE);
        SchemeList exp = list(form, new TestObject(), new TestObject());
        interpreter.eval(exp, env);
    }

    @Test
    public void evalWhenGivenSymbolInQuoteSpecialFormThenReturnsSymbolAndNotLookUpSymbolInEnv() {
        Syntax form = new Syntax(Syntax.Special.QUOTE);
        SchemeList exp = list(form, new Symbol("Test"));
        assertSame(second(exp), interpreter.eval(exp, env));
        verify(env, never()).lookUp((Symbol)second(exp));
    }

    @Test(expected= SyntaxErrorException.class)
    public void evalWhenGivenMoreThanTwoOperandsWithSetSpecialFormThenSyntaxError() {
        Syntax form = new Syntax(Syntax.Special.SET);
        SchemeList exp = list(form, new Symbol("Test"), new TestObject(), new TestObject());
        interpreter.eval(exp, env);
    }

    @Test
    public void evalWhenGivenSymbolAndExpressionThenCallsSetOnEnv() {
        Syntax form = new Syntax(Syntax.Special.SET);
        SchemeList exp = list(form, new Symbol("Test"), new TestObject());
        assertEquals(new Unspecified(), interpreter.eval(exp, env));
        verify(env).set((Symbol)second(exp), third(exp));
    }
}
