import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program calculates the value of an expression consisting of numbers,
 * arithmetic operators, and parentheses.
 *
 * @author Zhen Liu
 *
 */
public final class ExpressionEvaluator {

    /**
     * Base used in number representation.
     */
    private static final int RADIX = 10;

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ExpressionEvaluator() {
    }

    /**
     * Evaluates a digit and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with a digit
     * @return value of the digit
     * @updates source
     * @requires 1 < |source| and [the first character of source is a digit]
     * @ensures <pre>
     * valueOfDigit = [value of the digit at the start of #source]  and
     * #source = [digit string at start of #source] * source
     * </pre>
     */
    private static int valueOfDigit(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value = Character.digit(source.charAt(0), RADIX);
        source.deleteCharAt(0);
        return value;
    }

    /**
     * Evaluates a digit sequence and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with a digit-seq string
     * @return value of the digit sequence
     * @updates source
     * @requires <pre>
     * [a digit-seq string is a proper prefix of source, which
     * contains a character that is not a digit]
     * </pre>
     * @ensures <pre>
     * valueOfDigitSeq =
     *   [value of longest digit-seq string at start of #source]  and
     * #source = [longest digit-seq string at start of #source] * source
     * </pre>
     */
    private static int valueOfDigitSeq(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value = valueOfDigit(source);
        while (Character.isDigit(source.charAt(0))) {
            value = value * RADIX + valueOfDigit(source);
        }
        return value;
    }

    /**
     * Evaluates a factor and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with a factor string
     * @return value of the factor
     * @updates source
     * @requires <pre>
     * [a factor string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any factor string]
     * </pre>
     * @ensures <pre>
     * valueOfFactor =
     *   [value of longest factor string at start of #source]  and
     * #source = [longest factor string at start of #source] * source
     * </pre>
     */
    private static int valueOfFactor(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value;
        if (source.charAt(0) == '(') {
            source.deleteCharAt(0);
            value = valueOfExpr(source);
            source.deleteCharAt(0);
        } else {
            value = valueOfDigitSeq(source);
        }
        return value;
    }

    /**
     * Evaluates a term and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with a term string
     * @return value of the term
     * @updates source
     * @requires <pre>
     * [a term string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any term string]
     * </pre>
     * @ensures <pre>
     * valueOfTerm =
     *   [value of longest term string at start of #source]  and
     * #source = [longest term string at start of #source] * source
     * </pre>
     */
    private static int valueOfTerm(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value = valueOfFactor(source);

        while (source.charAt(0) == '*' || source.charAt(0) == '/') {
            Character op = source.charAt(0);
            source.deleteCharAt(0);
            int nextFactor = valueOfFactor(source);
            if (op == '*') {
                value = value * nextFactor;
            } else {
                value = value / nextFactor;
            }

        }
        return value;
    }

    /**
     * Evaluates an expression and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with an expr string
     * @return value of the expression
     * @updates source
     * @requires <pre>
     * [an expr string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any expr string]
     * </pre>
     * @ensures <pre>
     * valueOfExpr =
     *   [value of longest expr string at start of #source]  and
     * #source = [longest expr string at start of #source] * source
     * </pre>
     */
    public static int valueOfExpr(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value = valueOfTerm(source);
        while (source.charAt(0) == '+' || source.charAt(0) == '-') {
            Character op = source.charAt(0);
            source.deleteCharAt(0);
            int nextTerm = valueOfTerm(source);
            if (op == '+') {
                value += nextTerm;
            } else {
                value -= nextTerm;
            }

        }
        return value;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        out.print("Enter an expression followed by !: ");
        String source = in.nextLine();
        while (source.length() > 0) {
            /*
             * Parse and evaluate the expression after removing all white space
             * (spaces and tabs) from the user input.
             */
            int value = valueOfExpr(
                    new StringBuilder(source.replaceAll("[ \t]", "")));
            out.println(
                    source.substring(0, source.length() - 1) + " = " + value);
            out.print("Enter an expression followed by !: ");
            source = in.nextLine();
        }
        in.close();
        out.close();
    }

    /**
     * Evaluates a Boolean expression and returns its value.
     *
     * @param tokens
     *            the {@code Queue<String>} that starts with a bool-expr string
     * @return value of the expression
     * @updates tokens
     * @requires [a bool-expr string is a prefix of tokens]
     * @ensures <pre>
     * valueOfBoolExpr =
     *   [value of longest bool-expr string at start of #tokens]  and
     * #tokens = [longest bool-expr string at start of #tokens] * tokens
     * </pre>
     */
    public static boolean valueOfBoolExpr(Queue<String> tokens) {
        boolean result;
        String expr = tokens.dequeue();
        while (expr.equals("NOT") || expr.equals("(")) {
            tokens.dequeue();
            if (expr.equals("NOT")) {
                result = !valueOfBoolExpr(tokens);
                tokens.dequeue();
            } else {
                result = valueOfBoolExpr(tokens);
                String op = tokens.dequeue();
                if (op.equals("AND")) {
                    result = result && valueOfBoolExpr(tokens);
                } else {
                    result = result || valueOfBoolExpr(tokens);
                }
                tokens.dequeue();
            }
        }
        if (expr.equals("T")) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
