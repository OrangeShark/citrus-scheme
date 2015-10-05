package interpreter.type;

import interpreter.type.SchemePair;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class SchemePairTest {
    @Test
    public void carWhenSchemePairShouldHaveSameObject() {
        TestObject obj = new TestObject();
        SchemePair pair = new SchemePair(obj);
        assertSame(pair.car(), obj);
    }

    @Test
    public void cdrWhenSchemePairShouldHaveSameObject() {
        TestObject obj = new TestObject();
        SchemePair pair = new SchemePair(new TestObject(), obj);
        assertSame(pair.cdr(), obj);
    }
}
