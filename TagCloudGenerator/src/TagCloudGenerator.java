import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine4;

/**
 * generate a tag cloud HTML file.
 *
 * @author Zhen Liu and Yingqi Gao
 */
public final class TagCloudGenerator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
        // no code needed here
    }

    /**
     * Compare {@code Integer}s in decreasing order.
     */
    private static class Count
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            return o2.value().compareTo(o1.value());
        }
    }

    /**
     * Compare {@code String}s in alphabetical order.
     */
    private static class StringA
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            return o1.key().compareToIgnoreCase(o2.key());
        }
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @updates charSet
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    public static void generateElements(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";
        Set<Character> temp = charSet.newInstance();
        for (int i = 0; i < str.length(); i++) {
            /*
             * check the repeated character
             */
            if (!temp.contains(str.charAt(i))) {
                temp.add(str.charAt(i));
            }
        }
        /*
         * replace the charSet
         */
        charSet.transferFrom(temp);
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    public static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";
        /*
         * initialize the return string
         */
        String result = text.substring(position, position + 1);
        boolean isSeparator = separators.contains(text.charAt(position));
        for (int i = position; i < text.length()
                && separators.contains(text.charAt(i)) == isSeparator; i++) {
            result = text.substring(position, i + 1);
        }
        return result;
    }

    /**
     * generate words and corresponding counts from the given file, and add them
     * into map.
     *
     * @updates countMap
     * @param countMap
     *            the map of words and counts
     * @param inputName
     *            the name of input file
     * @requires <pre>
     * [file named inputName exists but is not open]
     * </pre>
     * @ensures [countMap contains word -> count]
     */
    private static void generateMap(String inputName,
            Map<String, Integer> countMap) {
        SimpleReader in = new SimpleReader1L(inputName);
        Set<Character> se = new Set1L<>();
        /*
         * define the separators in the text line by line.
         */
        final int sixtyfive = 65;
        final int nintyone = 91;
        final int nintyseven = 97;
        final int onetwothree = 123;
        final int twofivesix = 256;
        String s = "";
        for (int i = 0; i < sixtyfive; i++) {
            s = s + (char) i;
        }
        for (int i = nintyone; i < nintyseven; i++) {
            s = s + (char) i;
        }
        for (int i = onetwothree; i < twofivesix; i++) {
            s = s + (char) i;
        }
        generateElements(s, se);
        /*
         * generate words from the input text.
         */
        while (!in.atEOS()) {
            String str = in.nextLine();
            int position = 0;
            while (position < str.length()) {

                String token = nextWordOrSeparator(str, position, se)
                        .toLowerCase();
                if (!se.contains(token.charAt(0))) {
                    if (countMap.hasKey(token)) {
                        /*
                         * increase the count.
                         */
                        int count = countMap.value(token) + 1;
                        countMap.replaceValue(token, count);
                    } else {
                        countMap.add(token, 1);
                    }
                }
                position += token.length();
            }
        }
        in.close();
    }

    /**
     * generate the tag cloud tags and output into an HTML file.
     *
     * @param countMap
     *            the map of words and corresponding counts
     * @param n
     *            the number of words in tag cloud
     * @param out
     *            the output stream
     * @updates output.content
     * @requires countMap is not null and the out.is_open
     * @ensures out.content = #out.content * [the HTML tag cloud tags]
     *
     */
    private static void generateTagCloud(Map<String, Integer> countMap, int n,
            SimpleWriter out) {
        assert countMap != null : "Violation of: Map is not null";
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        Comparator<Map.Pair<String, Integer>> c = new Count();
        SortingMachine<Map.Pair<String, Integer>> sm1 = new SortingMachine4<>(
                c);
        Comparator<Map.Pair<String, Integer>> w = new StringA();
        SortingMachine<Map.Pair<String, Integer>> sm2 = new SortingMachine4<>(
                w);
        for (Map.Pair<String, Integer> p : countMap) {
            sm1.add(p);
        }
        sm1.changeToExtractionMode();
        for (int i = 0; i < n; i++) {
            sm2.add(sm1.removeFirst());
        }
        sm2.changeToExtractionMode();
        while (sm2.size() > 0) {
            Map.Pair<String, Integer> newP = sm2.removeFirst();
            String word = newP.key();
            int count = newP.value();
            out.println("<span style=\"cursor:default\" class=\"f"
                    + ((count - 25) / 30 + 11) + "\" title=\"count: " + count
                    + "\">" + word + "</span>");
        }
    }

    /**
     * generate the "opening" tags for the output HTML file.
     *
     * @param inputName
     *            the name of the input file
     * @param n
     *            the number of words to generate in html file
     * @param out
     *            the output stream
     * @updates output.content
     * @requires inputName is not null and the out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     *
     */
    private static void outputHeader(String inputName, int n,
            SimpleWriter out) {
        assert inputName != null : "Violation of: input file name is not null";
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Top " + n + " words in " + inputName + "</title>");
        out.println("<link href=\"http://web.cse.ohio-state.edu/"
                + "software/2231/web-sw2/assignments/projects/"
                + "tag-cloud-generator/data/"
                + "tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println(
                "<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Top " + n + " words in " + inputName + "</h2>");
        out.println("<hr>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");
    }

    /**
     * generate the "ending" tags for the output HTML file.
     *
     * @param out
     *            the output stream
     * @updates output.content
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "ending" tags]
     *
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();
        out.println("Please enter the name of input file:");
        String inputName = in.nextLine();
        out.println("Please enter the name of output file:");
        String outputName = in.nextLine();
        out.println("Please enter the number of words in tag cloud:");
        int n = in.nextInteger();
        SimpleWriter fileout = new SimpleWriter1L(outputName);
        Map<String, Integer> countMap = new Map1L<>();
        generateMap(inputName, countMap);
        outputHeader(inputName, n, fileout);
        generateTagCloud(countMap, n, fileout);
        outputFooter(fileout);
        out.close();
        fileout.close();
        in.close();
    }

}
