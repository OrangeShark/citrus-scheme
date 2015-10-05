package interpreter;

import interpreter.Environment;
import interpreter.UnboundVariableException;
import interpreter.type.TestObject;

import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

public class EnvironmentTest {

    private Environment testEnv;
    private TestObject obj;
    private String key = "test";

    @Before
    public void setUp() {
        testEnv = new Environment();
        obj = new TestObject();
        testEnv.define(key, obj);
    }

    @Test
    public void lookUpWhenVariableBoundShouldReturnObject() {
        assertSame(testEnv.lookUp(key), obj);
    }

    @Test
    public void lookUpWhenVariableBoundInParentEnvironmentShouldReturnObject() {
        Environment env = new Environment(testEnv, null, null);
        assertSame(env.lookUp(key), obj);
    }

    @Test(expected= UnboundVariableException.class)
    public void lookUpWhenVariableNotBoundInEnvironmentShouldThrowUnboundVariableException() {
        testEnv.lookUp("foo");
    }

    @Test(expected= UnboundVariableException.class)
    public void lookUpWhenVariableNotBoundInEnvironmentAndParentEnvironmentShouldThrowUnboundVariableException() {
        Environment env = new Environment(testEnv, null, null);
        testEnv.lookUp("foo");
    }

    @Test
    public void setWhenVariableBoundInEnvironmentShouldSetToNewObject() {
        TestObject newObj = new TestObject();
        testEnv.set(key, newObj);
        assertSame(testEnv.lookUp(key), newObj);
    }

    @Test
    public void setWhenVariableBoundInParentEnvironmentShouldSetToNewObjectInParentEnvironment() {
        TestObject newObj = new TestObject();
        Environment env = new Environment(testEnv, null, null);
        env.set(key, newObj);
        assertSame(testEnv.lookUp(key), newObj);
    }

    @Test(expected= UnboundVariableException.class)
    public void setWhenVariableNotBoundInEnvironmentShouldThrowUnboundVariableException() {
        TestObject newObj = new TestObject();
        testEnv.set("foo", newObj);
    }
}
