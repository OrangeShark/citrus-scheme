package citrus.scheme.base;

import citrus.type.*;
import citrus.scheme.base.equivalence.Eqv;

import static citrus.util.List.*;

import java.lang.IllegalArgumentException;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EqvTest {
    private Eqv eqv;

    @Before
    public void beforeTest() {
        eqv = new Eqv();
    }

    @Test(expected= IllegalArgumentException.class)
    public void eqvWhenGivenOneArgumentThrowsException() {
        SchemeList args = list(new TestObject());
        eqv.apply(args);
    }

    @Test(expected= IllegalArgumentException.class)
    public void eqvWhenGivenThreeArgumentsThrowsException() {
        SchemeList args = list(new TestObject(), new TestObject(), new TestObject());
        eqv.apply(args);
    }

    @Test
    public void eqvWhenGivenTwoTrueBoolsThenReturnsTrue() {
        SchemeList args = list(new Bool(true), new Bool(true));
        assertEquals(new Bool(true), eqv.apply(args));
    }

    @Test
    public void eqvWhenGivenTwoFalseBoolsThenReturnsTrue() {
        SchemeList args = list(new Bool(false), new Bool(false));
        assertEquals(new Bool(true), eqv.apply(args));
    }

    @Test
    public void eqvWhenGivenOneTrueAndOneFalseBoolThenReturnsFalse() {
        SchemeList args = list(new Bool(true), new Bool(false));
        assertEquals(new Bool(false), eqv.apply(args));
    }

    @Test
    public void eqvWhenGivenTwoSymbolsAndAreTheSameThenReturnsTrue() {
        SchemeList args = list(new Symbol("test"), new Symbol("test"));
        assertEquals(new Bool(true), eqv.apply(args));
    }

    @Test
    public void eqvWhenGiveTwoSymbolsAndAreDifferentThenReturnsFalse() {
        SchemeList args = list(new Symbol("foo"), new Symbol("false"));
        assertEquals(new Bool(false), eqv.apply(args));
    }
}
