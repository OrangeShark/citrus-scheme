package interpreter;

import interpreter.Interpreter;
import interpreter.Environment;
import interpreter.type.*;
import interpreter.type.TestObject;

import static interpreter.util.List.*;

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
        assertSame(interpreter.eval(sym, env), obj);
    }

    @Test
    public void evalWhenNotGivenPairOrSymbolObjectThenReturnsObject() {
        TestObject obj = new TestObject();
        assertSame(interpreter.eval(obj, env), obj);
    }

    @Test
    public void evalWhenGivenPairWithApplicableOpThenCallsApplyAndReturnsResult() {
        Primitive op = mock(Primitive.class);
        TestObject obj = new TestObject();
        Pair args = new Pair(new TestObject());
        Pair exp = new Pair(op, args);
        when(op.apply(interpreter, args)).thenReturn(obj);
        assertSame(interpreter.eval(exp, env), obj);
    }

    @Test
    public void evalWhenGivenPairWithIfSpecialFormAndTrueExpressionThenReturnsSecondOperand() {
        Syntax form = new Syntax(Syntax.Special.IF);
        SchemeList operands = list(new Bool(true), new TestObject());
        SchemeList exp = new Pair(form, operands);
        assertSame(interpreter.eval(exp, env), second(operands));
    }

    @Test
    public void evalWHenGivenPairWithIfSpecialFormAndFalseExpressionWithNoElseReturnsUnspecified() {
        Syntax form = new Syntax(Syntax.Special.IF);
        SchemeList operands = list(new Bool(false), new TestObject());
        SchemeList exp = new Pair(form, operands);
        assertEquals(interpreter.eval(exp, env), new Unspecified());
    }
}
