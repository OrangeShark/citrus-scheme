package interpreter.type;

import interpreter.type.SchemePair;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class SchemePairTest {
    @Test
    public void carWhenSchemePairShouldHaveSameObject() {
        SchemeNumber num = new SchemeNumber(1.0);
        SchemePair pair = new SchemePair(num);
        assertSame(pair.car(), num);
    }

    @Test
    public void cdrWhenSchemePairShouldHaveSameObject() {
        SchemeNumber num = new SchemeNumber(1.0);
        SchemePair pair = new SchemePair(new SchemeNumber(0.0), num);
        assertSame(pair.cdr(), num);
    }
}
