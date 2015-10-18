package interpreter;

import interpreter.Interpreter;
import interpreter.Environment;
import interpreter.type.Pair;
import interpreter.type.Primitive;
import interpreter.type.Symbol;
import interpreter.type.TestObject;


import static org.junit.Assert.assertSame;

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
}
