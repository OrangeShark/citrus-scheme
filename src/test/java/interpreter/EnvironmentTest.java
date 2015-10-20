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
package interpreter;

import interpreter.Environment;
import interpreter.UnboundVariableException;
import interpreter.type.TestObject;
import interpreter.type.Symbol;
import interpreter.type.Null;

import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

public class EnvironmentTest {

    private Environment testEnv;
    private TestObject obj;
    private Symbol key;

    @Before
    public void setUp() {
        testEnv = new Environment();
        obj = new TestObject();
        key = new Symbol("test");
        testEnv.define(key, obj);
    }

    @Test
    public void lookUpWhenVariableBoundShouldReturnObject() {
        assertSame(obj, testEnv.lookUp(key));
    }

    @Test
    public void lookUpWhenVariableBoundInParentEnvironmentShouldReturnObject() {
        Environment env = new Environment(testEnv, new Null(), new Null());
        assertSame(obj, env.lookUp(key));
    }

    @Test(expected= UnboundVariableException.class)
    public void lookUpWhenVariableNotBoundInEnvironmentShouldThrowUnboundVariableException() {
        testEnv.lookUp(new Symbol("foo"));
    }

    @Test(expected= UnboundVariableException.class)
    public void lookUpWhenVariableNotBoundInEnvironmentAndParentEnvironmentShouldThrowUnboundVariableException() {
        Environment env = new Environment(testEnv, new Null(), new Null());
        testEnv.lookUp(new Symbol("foo"));
    }

    @Test
    public void setWhenVariableBoundInEnvironmentShouldSetToNewObject() {
        TestObject newObj = new TestObject();
        testEnv.set(key, newObj);
        assertSame(newObj, testEnv.lookUp(key));
    }

    @Test
    public void setWhenVariableBoundInParentEnvironmentShouldSetToNewObjectInParentEnvironment() {
        TestObject newObj = new TestObject();
        Environment env = new Environment(testEnv, new Null(), new Null());
        env.set(key, newObj);
        assertSame(newObj, testEnv.lookUp(key));
    }

    @Test(expected= UnboundVariableException.class)
    public void setWhenVariableNotBoundInEnvironmentShouldThrowUnboundVariableException() {
        TestObject newObj = new TestObject();
        testEnv.set(newObj, new Symbol("foo"));
    }
}
