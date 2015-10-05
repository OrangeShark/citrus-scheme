package interpreter.type;

import interpreter.type.SchemeObject;

import java.lang.IllegalArgumentException;

import org.junit.Test;

public class SchemeObjectTest {
    private class TestObject extends SchemeObject {

    }

    @Test(expected= IllegalArgumentException.class)
    public void carWhenSchemeObjectShouldThrowIllegalArgumentException() {
        new TestObject().car();
    }

    @Test(expected= IllegalArgumentException.class)
    public void cdrWhenSchemeObjectShouldThrowIllegalArgumentException() {
        new TestObject().cdr();
    }
}
