import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Yingqi Gao and Zhen Liu
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * test for constructor.
     */
    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /*
     * Test cases for kernel methods
     */
    /**
     * test for add on empty set.
     */
    @Test
    public final void testAddEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        s.add("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * test for add on set of size 1.
     */
    @Test
    public final void testAddNonEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red");
        Set<String> sExpected = this.createFromArgsRef("red", "blue");
        /*
         * Call method under test
         */
        s.add("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * test for add on set of size more than 1.
     */
    @Test
    public final void testAddNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "blue", "green");
        Set<String> sExpected = this.createFromArgsRef("red", "blue", "green",
                "yellow");
        /*
         * Call method under test
         */
        s.add("yellow");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * test for remove element and leaving empty set.
     */
    @Test
    public final void testRemoveLeavingEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red");
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        String x = s.remove("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("red", x);
    }

    /**
     * test for remove element leaving set of size one and the right tree is
     * empty.
     */
    @Test
    public final void testRemoveLeavingNonEmptyOne1() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "blue");
        Set<String> sExpected = this.createFromArgsRef("blue");
        /*
         * Call method under test
         */
        String x = s.remove("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("red", x);
    }

    /**
     * test for remove element leaving set of size one and the right tree is not
     * empty.
     */
    @Test
    public final void testRemoveLeavingNonEmptyOne2() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "blue", "violet");
        Set<String> sExpected = this.createFromArgsRef("blue", "violet");
        /*
         * Call method under test
         */
        String x = s.remove("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("red", x);
    }

    /**
     * test for removing element which is not the root of the tree and leaving
     * set of size more than one.
     */
    @Test
    public final void testRemoveLeavingNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "green", "blue");
        Set<String> sExpected = this.createFromArgsRef("red", "blue");
        /*
         * Call method under test
         */
        String x = s.remove("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("green", x);
    }

    /**
     * test removeAny method on the set of size one.
     */
    @Test
    public final void testRemoveAnyLeavingEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red");
        Set<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        String x = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(x));
        sExpected.remove(x);
        assertEquals(sExpected, s);
    }

    /**
     * test removeAny method leaving one element.
     */
    @Test
    public final void testRemoveAnyLeavingNonEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "blue");
        Set<String> sExpected = this.createFromArgsRef("red", "blue");
        /*
         * Call method under test
         */
        String x = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(x));
        sExpected.remove(x);
        assertEquals(sExpected, s);
    }

    /**
     * test removeAny method leaving more than one element.
     */
    @Test
    public final void testRemoveAnyLeavingNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "green", "blue");
        Set<String> sExpected = this.createFromArgsRef("red", "green", "blue");
        /*
         * Call method under test
         */
        String x = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(x));
        sExpected.remove(x);
        assertEquals(sExpected, s);
    }

    /**
     * test contains method on the set of size one.
     */
    @Test
    public final void testContainsOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red");
        Set<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        boolean result = s.contains("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, result);
        assertEquals(sExpected, s);
    }

    /**
     * test contains method on the set of size more than one.
     */
    @Test
    public final void testContainsMoreThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "green", "blue");
        Set<String> sExpected = this.createFromArgsRef("red", "green", "blue");
        /*
         * Call method under test
         */
        boolean result = s.contains("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, result);
        assertEquals(sExpected, s);
    }

    /**
     * test contains method that the result is false.
     */
    @Test
    public final void testNotContainsMoreThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "green", "blue");
        Set<String> sExpected = this.createFromArgsRef("red", "green", "blue");
        /*
         * Call method under test
         */
        boolean result = s.contains("yellow");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, result);
        assertEquals(sExpected, s);
    }

    /**
     * test for size method on empty set.
     */
    @Test
    public final void testSizeEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        int i = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(0, i);
    }

    /**
     * test for size method on set of size one.
     */
    @Test
    public final void testSizeNonEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red");
        Set<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        int i = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(1, i);
    }

    /**
     * test for size method on set of size more than one.
     */
    @Test
    public final void testSizeNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "green", "blue");
        Set<String> sExpected = this.createFromArgsRef("red", "green", "blue");
        /*
         * Call method under test
         */
        int i = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        final int ans = 3;
        assertEquals(ans, i);
    }

}
