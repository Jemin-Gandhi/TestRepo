import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Zhen Liu and Yingqi Gao
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size
    @Test
    public final void testAddNotEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red", "yellow");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstLeavingEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.removeFirst();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstLeavingNotEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "red", "yellow");
        m.removeFirst();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testchangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testchangeToExtractionModeNotEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "yellow");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red", "yellow");
        boolean result = m.isInInsertionMode();
        assertEquals(true, result);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "yellow");
        boolean result = m.isInInsertionMode();
        assertEquals(false, result);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrder() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "yellow");
        assertEquals(mExpected.order(), m.order());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSize1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red", "yellow");
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSize2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "yellow");
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }
}
