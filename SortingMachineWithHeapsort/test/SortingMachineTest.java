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

    /**
     * test for constructor.
     */
    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    /**
     * test to add element on empty sorting machine.
     */
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    /**
     * test to add element on not empty sorting machine.
     */
    @Test
    public final void testAddNotEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red", "yellow");
        m.add("green");
        assertEquals(mExpected, m);
    }

    /**
     * test to change the insertion mode to extraction mode.
     */
    @Test
    public final void testchangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    /**
     * test to change the mode of insertion mode to extraction mode on not empty
     * sm.
     */
    @Test
    public final void testchangeToExtractionModeNotEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "yellow");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    /**
     * test to remove the first element and leaving the sorting machine empty.
     */
    @Test
    public final void testRemoveFirstLeavingEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.removeFirst();
        assertEquals(mExpected, m);
    }

    /**
     * test to remove the first element from sorting machine leaving not empty.
     * test when the siftdown method called on only left child exists.
     */
    @Test
    public final void testRemoveFirstLeavingNotEmpty1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "red", "yellow");
        m.removeFirst();
        assertEquals(mExpected, m);
    }

    /**
     * test to remove the first element from sorting machine leaving not empty.
     * test when the siftdown method called on both left and right children
     * exist.
     */
    @Test
    public final void testRemoveFirstLeavingNotEmpty2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "purple", "yellow", "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "purple", "yellow", "red");
        m.removeFirst();
        assertEquals(mExpected, m);
    }

    /**
     * test for isInsertionMode method on empty sm and the result should be
     * true.
     */
    @Test
    public final void testIsInInsertionModeEmptyTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        boolean result = m.isInInsertionMode();
        assertEquals(true, result);
        assertEquals(mExpected, m);
    }

    /**
     * test for isInsertionMode method on non-empty sm and the result should be
     * true.
     */
    @Test
    public final void testIsInInsertionModeNonEmptyTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red", "yellow");
        boolean result = m.isInInsertionMode();
        assertEquals(true, result);
        assertEquals(mExpected, m);
    }

    /**
     * test for isInsertionMode method on empty sm and the result should be
     * false.
     */
    @Test
    public final void testIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        boolean result = m.isInInsertionMode();
        assertEquals(false, result);
        assertEquals(mExpected, m);
    }

    /**
     * test for isInsertionMode method on non-empty sm and the result should be
     * false.
     */
    @Test
    public final void testIsInInsertionModeNonEmptyFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "yellow");
        boolean result = m.isInInsertionMode();
        assertEquals(false, result);
        assertEquals(mExpected, m);
    }

    /**
     * test the order method on empty sm.
     */
    @Test
    public final void testOrderEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(mExpected.order(), m.order());
        assertEquals(mExpected, m);
    }

    /**
     * test the order method on non-empty sm.
     */
    @Test
    public final void testOrderNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "yellow");
        assertEquals(mExpected.order(), m.order());
        assertEquals(mExpected, m);
    }

    /**
     * test the size method when the empty sorting machine is insertion mode.
     */
    @Test
    public final void testSizeInsertionEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    /**
     * test the size method when the not empty sorting machine is insertion
     * mode.
     */
    @Test
    public final void testSizeInsertionNotEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red", "yellow");
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    /**
     * test the size method when the empty sorting machine is extraction mode.
     */
    @Test
    public final void testSizeExtractionEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    /**
     * test the size method when the not empty sorting machine is extraction
     * mode.
     */
    @Test
    public final void testSizeExtractionNotEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "yellow");
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    /**
     * test the size method when the not empty sorting machine is extraction
     * mode and the heap size is not equal to the length of heap array.
     */
    @Test
    public final void testSizeExtractionNotEmptyRemove() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "yellow");
        m.removeFirst();
        mExpected.removeFirst();
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }
}
