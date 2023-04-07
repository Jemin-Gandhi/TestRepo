import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Zhen Liu and Yingqi Gao
 *
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     *
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires [c is a condition string]
     * @ensures parseCondition = [Condition corresponding to c]
     */
    private static Condition parseCondition(String c) {
        assert c != null : "Violation of: c is not null";
        assert Tokenizer
                .isCondition(c) : "Violation of: c is a condition string";
        return Condition.valueOf(c.replace('-', '_').toUpperCase());
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"IF"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF") : ""
                + "Violation of: <\"IF\"> is proper prefix of tokens";

        tokens.dequeue(); //dequeue the "IF" keyword.
        String condition = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(condition),
                "Shoud be a valid condition.");
        String then = tokens.dequeue();
        Reporter.assertElseFatalError(then.equals("THEN"),
                "Keyword THEN missing.");
        Statement sub1 = s.newInstance();
        sub1.parseBlock(tokens);
        if (tokens.front().equals("ELSE")) {
            tokens.dequeue();
            Statement sub2 = s.newInstance();
            sub2.parseBlock(tokens);
            s.parseBlock(tokens);
            String end = tokens.dequeue();
            Reporter.assertElseFatalError(end.equals("END"),
                    "Keyword END missing.");
            String iF = tokens.dequeue();
            Reporter.assertElseFatalError(iF.equals("IF"),
                    "Keyword IF missing");
            s.assembleIfElse(parseCondition(condition), sub1, sub2);
        } else {
            String end = tokens.dequeue();
            Reporter.assertElseFatalError(end.equals("END"),
                    "Keyword END missing");
            String iF = tokens.dequeue();
            Reporter.assertElseFatalError(iF.equals("IF"),
                    "Keyword IF missing");
            s.assembleIf(parseCondition(condition), sub1);
        }

    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"WHILE"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE") : ""
                + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        tokens.dequeue(); //dequeue the "IF" keyword.
        String condition = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(condition),
                "Shoud be a valid condition.");
        String dO = tokens.dequeue();
        Reporter.assertElseFatalError(dO.equals("DO"), "Keyword DO missing.");
        Statement sub = s.newInstance();
        sub.parseBlock(tokens);
        String end = tokens.dequeue();
        Reporter.assertElseFatalError(end.equals("END"),
                "Keyword END missing.");
        String whilE = tokens.dequeue();
        Reporter.assertElseFatalError(whilE.equals("WHILE"),
                "Keyword WHILE missing.");
        s.assembleWhile(parseCondition(condition), sub);

    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [identifier string is a proper prefix of tokens]
     * @ensures <pre>
     * s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0
                && Tokenizer.isIdentifier(tokens.front()) : ""
                        + "Violation of: identifier string is proper prefix of tokens";

        String call = tokens.dequeue();
        s.assembleCall(call);

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        String prefix = tokens.front();
        Reporter.assertElseFatalError(
                prefix.equals("IF") || prefix.equals("WHILE")
                        || Tokenizer.isIdentifier(prefix),
                "The prefix is not valid.");
        if (prefix.equals("IF")) {
            parseIf(tokens, this);
        } else if (prefix.equals("WHILE")) {
            parseWhile(tokens, this);
        } else {
            parseCall(tokens, this);
        }

    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        String prefix = tokens.front();

        while (!prefix.equals("END") && !prefix.equals("ELSE")
                && !prefix.equals(Tokenizer.END_OF_INPUT)) {
            Reporter.assertElseFatalError(
                    prefix.equals("IF") || prefix.equals("WHILE")
                            || Tokenizer.isIdentifier(prefix),
                    "The " + prefix + " is not valid.");
            Statement s = this.newInstance();
            s.parse(tokens);
            this.addToBlock(this.lengthOfBlock(), s);
            prefix = tokens.front();
        }

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parse(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
