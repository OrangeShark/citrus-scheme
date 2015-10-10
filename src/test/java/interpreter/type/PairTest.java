package interpreter.type;

import interpreter.type.Pair;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class PairTest {
    @Test
    public void carWhenPairShouldHaveSameObject() {
        TestObject obj = new TestObject();
        Pair pair = new Pair(obj);
        assertSame(pair.car(), obj);
    }

    @Test
    public void cdrWhenPairShouldHaveSameObject() {
        TestObject obj = new TestObject();
        Pair pair = new Pair(new TestObject(), obj);
        assertSame(pair.cdr(), obj);
    }
}
