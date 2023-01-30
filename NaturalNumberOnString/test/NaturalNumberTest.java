import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /*
     * Test StackKernel constructor(s) and methods.
     */
    /**
     * test constructor for no argument.
     */
    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * test constructor for integer argument of 0.
     */
    @Test
    public final void testIntArgumentZeroConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * test constructor for integer argument of max integer.
     */
    @Test
    public final void testIntArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        int max = Integer.MAX_VALUE;
        NaturalNumber n = this.constructorTest(max);
        NaturalNumber nExpected = this.constructorRef(max);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * test constructor for String argument of 0.
     */
    @Test
    public final void testStringArgumentZeroConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("0");
        NaturalNumber nExpected = this.constructorRef("0");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * test constructor for String argument of max integer.
     */
    @Test
    public final void testStringArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("2147483647");
        NaturalNumber nExpected = this.constructorRef("2147483647");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * test constructor for NaturalNumber argument of 0.
     */
    @Test
    public final void testNNArgumentZeroConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(new NaturalNumber2(0));
        NaturalNumber nExpected = this.constructorRef(new NaturalNumber2(0));
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * test constructor for NaturalNumber argument of max of integer.
     */
    @Test
    public final void testNNArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this
                .constructorTest(new NaturalNumber2("2147483647"));
        NaturalNumber nExpected = this
                .constructorRef(new NaturalNumber2("2147483647"));
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * test 0 mutiplyBy10 add 0.
     */
    @Test
    public final void testZeroMutiplyBy10AddZero() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest("0");
        NaturalNumber nExpected = this.constructorRef("0");
        /*
         * Call method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * test 0 mutiplyBy10 add 9.
     */
    @Test
    public final void testZeroMutiplyBy10AddNine() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest("0");
        NaturalNumber nExpected = this.constructorRef("9");
        /*
         * Call method under test
         */
        final int nine = 9;
        n.multiplyBy10(nine);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * test 10 mutiplyBy10 add 0.
     */
    @Test
    public final void testTenMutiplyBy10AddZero() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest("10");
        NaturalNumber nExpected = this.constructorRef("100");
        /*
         * Call method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * test 10 mutiplyBy10 add 9.
     */
    @Test
    public final void testTenMutiplyBy10AddNine() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest("10");
        NaturalNumber nExpected = this.constructorRef("109");
        /*
         * Call method under test
         */
        final int nine = 9;
        n.multiplyBy10(nine);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * test max integer mutiplyBy10 add 1.
     */
    @Test
    public final void testMaxMutiplyBy10AddOne() {
        /*
         * Set up variables
         */
        int max = Integer.MAX_VALUE;
        NaturalNumber n = this.constructorTest(max);
        NaturalNumber nExpected = this.constructorRef(max);
        nExpected.multiplyBy10(1);
        /*
         * Call method under test
         */
        n.multiplyBy10(1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * test 0 divideBy10.
     */
    @Test
    public final void testZeroDivideBy10() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Call method under test
         */
        int result = n.divideBy10();
        int expected = nExpected.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expected, result);
        assertEquals(nExpected, n);
    }

    /**
     * test 1 divideBy10.
     */
    @Test
    public final void testOneDivideBy10() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(1);
        NaturalNumber nExpected = this.constructorRef(1);
        /*
         * Call method under test
         */
        int result = n.divideBy10();
        int expected = nExpected.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expected, result);
        assertEquals(nExpected, n);
    }

    /**
     * test max integer divideBy10.
     */
    @Test
    public final void testMaxDivideBy10() {
        /*
         * Set up variables
         */
        int max = Integer.MAX_VALUE;
        NaturalNumber n = this.constructorTest(max);
        NaturalNumber nExpected = this.constructorRef(max);
        /*
         * Call method under test
         */
        int result = n.divideBy10();
        int expected = nExpected.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(expected, result);
        assertEquals(nExpected, n);
    }

    /**
     * test isZero true.
     */
    @Test
    public final void testIsZeroTrue() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Call method under test
         */
        boolean result = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, result);
        assertEquals(nExpected, n);
    }

    /**
     * test isZero false.
     */
    @Test
    public final void testIsZeroFalse() {
        /*
         * Set up variables
         */
        int max = Integer.MAX_VALUE;
        NaturalNumber n = this.constructorTest(max);
        NaturalNumber nExpected = this.constructorRef(max);
        /*
         * Call method under test
         */
        boolean result = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, result);
        assertEquals(nExpected, n);
    }
}
