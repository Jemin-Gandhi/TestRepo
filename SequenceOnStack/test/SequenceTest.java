import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.sequence.Sequence;

/**
 * JUnit test fixture for {@code Sequence<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class SequenceTest {

    /**
     * Invokes the appropriate {@code Sequence} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new sequence
     * @ensures constructorTest = <>
     */
    protected abstract Sequence<String> constructorTest();

    /**
     * Invokes the appropriate {@code Sequence} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new sequence
     * @ensures constructorRef = <>
     */
    protected abstract Sequence<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsTest = [entries in args]
     */
    private Sequence<String> createFromArgsTest(String... args) {
        Sequence<String> sequence = this.constructorTest();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsRef = [entries in args]
     */
    private Sequence<String> createFromArgsRef(String... args) {
        Sequence<String> sequence = this.constructorRef();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Sequence<String> s = this.constructorTest();
        Sequence<String> sExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /*
     * Test cases for kernel methods
     */

    @Test
    public final void testEnqueueEmpty() {
        /*
         * Set up variables
         */
        Sequence<String> s = this.createFromArgsTest();
        Sequence<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        s.add(s.length(), "red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testEnqueueNonEmptyOne() {
        /*
         * Set up variables
         */
        Sequence<String> s = this.createFromArgsTest("red");
        Sequence<String> sExpected = this.createFromArgsRef("red", "blue");
        /*
         * Call method under test
         */
        s.add(s.length(), "blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testEnqueueNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Sequence<String> s = this.createFromArgsTest("red", "blue", "green");
        Sequence<String> sExpected = this.createFromArgsRef("red", "blue",
                "green", "yellow");
        /*
         * Call method under test
         */
        s.add(s.length(), "yellow");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testDequeueLeavingEmpty() {
        /*
         * Set up variables
         */
        Sequence<String> s = this.createFromArgsTest("red");
        Sequence<String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        String x = s.remove(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("red", x);
    }

    @Test
    public final void testDequeueLeavingNonEmptyOne() {
        /*
         * Set up variables
         */
        Sequence<String> s = this.createFromArgsTest("red", "blue");
        Sequence<String> sExpected = this.createFromArgsRef("blue");
        /*
         * Call method under test
         */
        String x = s.remove(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("red", x);
    }

    @Test
    public final void testDequeueLeavingNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Sequence<String> s = this.createFromArgsTest("red", "green", "blue");
        Sequence<String> sExpected = this.createFromArgsRef("green", "blue");
        /*
         * Call method under test
         */
        String x = s.remove(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("red", x);
    }

    @Test
    public final void testLengthEmpty() {
        /*
         * Set up variables
         */
        Sequence<String> s = this.createFromArgsTest();
        Sequence<String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        int i = s.length();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(0, i);
    }

    @Test
    public final void testLengthNonEmptyOne() {
        /*
         * Set up variables
         */
        Sequence<String> s = this.createFromArgsTest("red");
        Sequence<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        int i = s.length();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(1, i);
    }

    @Test
    public final void testLengthNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Sequence<String> s = this.createFromArgsTest("red", "green", "blue");
        Sequence<String> sExpected = this.createFromArgsRef("red", "green",
                "blue");
        /*
         * Call method under test
         */
        int i = s.length();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(3, i);
    }

}
