import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test fixture for {@code ExpressionEvaluator}'s {@code valueOfExpr}
 * static method.
 *
 * @author Zhen Liu
 *
 */
public final class ExpressionEvaluatorTest {

    /**
     * sample test.
     */
    @Test
    //281/7/2-1-5*(15-(14-1))+((1))+20
    public void testExample() {
        StringBuilder exp = new StringBuilder(
                "281/7/2-1-5*(15-(14-1))+((1))+20=30!");
        final int result = 30;
        int value = ExpressionEvaluator.valueOfExpr(exp);
        assertEquals(result, value);
        assertEquals("=30!", exp.toString());
    }

    // TODO - add other (simpler) test cases to help with debugging
    /**
     * the easiest case.
     */
    @Test
    public void testNumber() {
        StringBuilder exp = new StringBuilder("130=130!");
        final int result = 130;
        int value = ExpressionEvaluator.valueOfExpr(exp);
        assertEquals(result, value);
        assertEquals("=130!", exp.toString());
    }

    /**
     * test plus and subtract.
     */
    @Test
    public void testPlusSubtract() {
        StringBuilder exp = new StringBuilder("100+20-12=108!");
        final int result = 108;
        int value = ExpressionEvaluator.valueOfExpr(exp);
        assertEquals(result, value);
        assertEquals("=108!", exp.toString());
    }

    /**
     * test times and divides.
     */
    @Test
    public void testTimeDivid() {
        StringBuilder exp = new StringBuilder("100/20*3=15!");
        final int result = 15;
        int value = ExpressionEvaluator.valueOfExpr(exp);
        assertEquals(result, value);
        assertEquals("=15!", exp.toString());
    }

    /**
     * test ().
     */
    @Test
    public void testParenthesis1() {
        StringBuilder exp = new StringBuilder("(10+5)=15!");
        final int result = 15;
        int value = ExpressionEvaluator.valueOfExpr(exp);
        assertEquals(result, value);
        assertEquals("=15!", exp.toString());
    }

    /**
     * test ().
     */
    @Test
    public void testParenthesis2() {
        StringBuilder exp = new StringBuilder("(10+5)/5*(12-4)=24!");
        final int result = 24;
        int value = ExpressionEvaluator.valueOfExpr(exp);
        assertEquals(result, value);
        assertEquals("=24!", exp.toString());
    }

    /**
     * test a complex case.
     */
    @Test
    public void testComplex() {
        StringBuilder exp = new StringBuilder("(15/3*(6+2)-(14-(15-2)))/3=13!");
        final int result = 13;
        int value = ExpressionEvaluator.valueOfExpr(exp);
        assertEquals(result, value);
        assertEquals("=13!", exp.toString());
    }
}
