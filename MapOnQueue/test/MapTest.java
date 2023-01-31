import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     * test for constructor.
     */
    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> s = this.constructorTest();
        Map<String, String> sExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * test for add1.
     */
    @Test
    public final void testAddEmpty() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple");
        /*
         * Call method under test
         */
        s.add("red", "apple");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * test for add2.
     */
    @Test
    public final void testAddOnMore() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple",
                "yellow", "banana");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple",
                "yellow", "banana", "purple", "grape");
        /*
         * Call method under test
         */
        s.add("purple", "grape");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * test for remove1.
     */
    @Test
    public final void testRemoveLeaveEmpty() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple");
        /*
         * Call method under test
         */
        Pair<String, String> p = s.remove("red");
        Pair<String, String> pExpected = s.remove("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(pExpected, p);
    }

    /**
     * test for remove2.
     */
    @Test
    public final void testRemoveLeaveNotEmpty() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple",
                "yellow", "banana", "purple", "grape");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple",
                "yellow", "banana", "purple", "grape");
        /*
         * Call method under test
         */
        Pair<String, String> p = s.remove("purple");
        Pair<String, String> pExpected = s.remove("purple");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(pExpected, p);

    }

    /**
     * test for removeAny1.
     */
    @Test
    public final void testRemoveAnyLeaveEmpty() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple");
        /*
         * Call method under test
         */
        Pair<String, String> p = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.hasKey(p.key()));
        Pair<String, String> pExpected = sExpected.remove(p.key());
        assertEquals(sExpected, s);
        assertEquals(pExpected, p);
    }

    /**
     * test for removeAny2.
     */
    @Test
    public final void testRemoveAnyLeaveNotEmpty() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple",
                "yellow", "banana", "purple", "grape");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple",
                "yellow", "banana", "purple", "grape");
        /*
         * Call method under test
         */
        Pair<String, String> p = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.hasKey(p.key()));
        Pair<String, String> pExpected = sExpected.remove(p.key());
        assertEquals(sExpected, s);
        assertEquals(pExpected, p);
    }

    /**
     * test for value1.
     */
    @Test
    public final void testValueOne() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple");
        /*
         * Call method under test
         */
        String value = s.value("red");
        String valueExpected = s.value("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(valueExpected, value);
    }

    /**
     * test for value2.
     */
    @Test
    public final void testValueMoreThanOne() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple",
                "yellow", "banana", "purple", "grape");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple",
                "yellow", "banana", "purple", "grape");
        /*
         * Call method under test
         */
        String value = s.value("yellow");
        String valueExpected = s.value("yellow");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(valueExpected, value);
    }

    /**
     * test for hasKey1.
     */
    @Test
    public final void testhasKeyEmpty() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        boolean result = s.hasKey("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(false, result);
    }

    /**
     * test for hasKey2.
     */
    @Test
    public final void testhasKeyOneTrue() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple");
        /*
         * Call method under test
         */
        boolean result = s.hasKey("red");
        boolean resultExpected = sExpected.hasKey("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(resultExpected, result);
    }

    /**
     * test for hasKey3.
     */
    @Test
    public final void testhasKeyOneFalse() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple");
        /*
         * Call method under test
         */
        boolean result = s.hasKey("yellow");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(false, result);
    }

    /**
     * test for hasKey4.
     */
    @Test
    public final void testhasKeyMoreTrue() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple",
                "yellow", "banana", "purple", "grape");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple",
                "yellow", "banana", "purple", "grape");
        /*
         * Call method under test
         */
        boolean result = s.hasKey("yellow");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(true, result);
    }

    /**
     * test for hasKey5.
     */
    @Test
    public final void testhasKeyMoreFalse() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple",
                "yellow", "banana", "purple", "grape");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple",
                "yellow", "banana", "purple", "grape");
        /*
         * Call method under test
         */
        boolean result = s.hasKey("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(false, result);
    }

    /**
     * test for size1.
     */
    @Test
    public final void testSizeEmpty() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        int size = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(0, size);
    }

    /**
     * test for size2.
     */
    @Test
    public final void testSizeOne() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple");
        /*
         * Call method under test
         */
        int size = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(1, size);
    }

    /**
     * test for size3.
     */
    @Test
    public final void testSizeMore() {
        /*
         * Set up variables and call method under test
         */

        Map<String, String> s = this.createFromArgsTest("red", "apple",
                "yellow", "banana", "purple", "grape");
        Map<String, String> sExpected = this.createFromArgsRef("red", "apple",
                "yellow", "banana", "purple", "grape");
        /*
         * Call method under test
         */
        int size = s.size();
        /*
         * Assert that values of variables match expectations
         */
        final int ans = 3;
        assertEquals(sExpected, s);
        assertEquals(ans, size);
    }
}
