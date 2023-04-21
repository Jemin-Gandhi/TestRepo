import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            int result = o2.getValue().compareTo(o1.getValue());
            if (o2.getValue().equals(o1.getValue())) {
                result = o1.getKey().compareTo(o1.getKey());
            }
            return result;
        }
    }

    /**
     * Compare {@code String}s in alphabetical order.
     */
    private static class StringA
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            int result = o1.getKey().compareTo(o2.getKey());
            if (o1.getKey().equals(o2.getKey())) {
                result = o2.getValue().compareTo(o1.getValue());
            }
            return result;
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters in
     * {@code letters}) or "separator string" (maximal length string of
     * characters not in {@code letters}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param letters
     *            the {@code Set} of letter characters
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
            Set<Character> letters) {
        assert text != null : "Violation of: text is not null";
        assert letters != null : "Violation of: letters is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";
        /*
         * initialize the return string
         */
        String result = text.substring(position, position + 1);
        boolean isLetter = letters.contains(text.charAt(position));
        for (int i = position; i < text.length()
                && letters.contains(text.charAt(i)) == isLetter; i++) {
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
     * @param in
     *            the input stream
     * @requires <pre>
     * [file named inputName exists but is not open]
     * </pre>
     * @ensures [countMap contains word -> count]
     */
    private static void generateMap(String inputName,
            Map<String, Integer> countMap, BufferedReader in) {
        /*
         * define a set containing letters.
         */
        Set<Character> se = new HashSet<>();
        for (int i = 'a'; i <= 'z'; i++) {
            se.add((char) i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            se.add((char) i);
        }
        /*
         * generate words from the input text.
         */
        try {
            String str = in.readLine();
            while (str != null) {
                int position = 0;
                while (position < str.length()) {

                    String token = nextWordOrSeparator(str, position, se)
                            .toLowerCase();
                    if (se.contains(token.charAt(0))) {
                        if (countMap.containsKey(token)) {
                            /*
                             * increase the count.
                             */
                            int count = countMap.get(token) + 1;
                            countMap.replace(token, count);
                        } else {
                            countMap.put(token, 1);
                        }
                    }
                    position += token.length();
                }
                str = in.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading input file.");
        }

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
            PrintWriter out) {
        assert countMap != null : "Violation of: Map is not null";
        assert out != null : "Violation of: out is not null";

        /*
         * check if the map is empty, if it's empty, which means the inputFile
         * is blank, then output nothing in HTML file.
         */
        if (countMap.size() > 0) {
            final int initial = 11;
            final int difference = 37;
            Comparator<Map.Entry<String, Integer>> c = new Count();
            Comparator<Map.Entry<String, Integer>> w = new StringA();
            List<Map.Entry<String, Integer>> li1 = new LinkedList<>();
            for (Map.Entry<String, Integer> e : countMap.entrySet()) {
                li1.add(e);
            }
            /*
             * sort li1 using the count order.
             */
            Collections.sort(li1, c);
            /*
             * if the input n is greater than the total number of all words,
             * replace n with the size of map in order to output all words in
             * the map.
             *
             */
            int newN = n;
            if (n > li1.size()) {
                newN = li1.size();
            }
            List<Map.Entry<String, Integer>> li2 = new LinkedList<>();
            /*
             * restore the max count.
             */
            Map.Entry<String, Integer> max = li1.remove(0);
            int tmax = max.getValue();
            li2.add(max);
            /*
             * add n-2 Map.Entrys into li2.
             */
            for (int i = 1; i < newN - 1; i++) {
                li2.add(li1.remove(0));
            }
            /*
             * restore the min count.
             */
            Map.Entry<String, Integer> min = li1.remove(0);
            int tmin = min.getValue();
            li2.add(min);
            /*
             * sort li2 with the string order.
             */
            Collections.sort(li2, w);
            while (li2.size() > 0) {
                Map.Entry<String, Integer> newP = li2.remove(0);
                String word = newP.getKey();
                int count = newP.getValue();
                /*
                 * process of calculating font size.
                 */
                int font = initial;
                if (count > tmin) {
                    font += difference * (count - tmin) / (tmax - tmin);
                }
                out.println("<span style=\"cursor:default\" class=\"f" + font
                        + "\" title=\"count: " + count + "\">" + word
                        + "</span>");
            }
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
    private static void outputHeader(String inputName, int n, PrintWriter out) {
        assert inputName != null : "Violation of: input file name is not null";
        assert out != null : "Violation of: out is not null";

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
    private static void outputFooter(PrintWriter out) {
        assert out != null : "Violation of: out is not null";

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
        Scanner in = new Scanner(System.in);
        /*
         * ask for name of input file.
         */
        System.out.println("Please enter the name of input file:");
        String inputName = in.nextLine();
        /*
         * ask for name of output file.
         */
        System.out.println("Please enter the name of output file:");
        String outputName = in.nextLine();
        /*
         * ask for number of words to output.
         */
        System.out.println("Please enter the number of words in tag cloud:");
        int n = in.nextInt();
        /*
         * close the input from console.
         */
        in.close();
        /*
         * declare the input from file.
         */
        BufferedReader filein;
        /*
         * handle IOEception when opening the input file.
         */
        try {
            filein = new BufferedReader(new FileReader(inputName));
        } catch (IOException e) {
            System.err.println("Error opening input file.");
            return;
        }
        /*
         * declare the output into file.
         */
        PrintWriter fileout;
        /*
         * handle IOEception when opening the output file.
         */
        try {
            fileout = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputName)));
        } catch (IOException e) {
            /*
             * if the output cannot be opened, output error message and close
             * the input from file.
             */
            System.err.println("Opening output file error.");
            try {
                filein.close();
            } catch (IOException e1) {
                System.err.println("Closing inputfile error.");
                return;
            }
            return;
        }
        Map<String, Integer> countMap = new HashMap<>();

        generateMap(inputName, countMap, filein);

        outputHeader(inputName, n, fileout);

        generateTagCloud(countMap, n, fileout);

        outputFooter(fileout);
        /*
         * close the fileout.
         */
        fileout.close();
        /*
         * handle IOException when closing filein.
         */
        try {
            filein.close();
        } catch (IOException e) {
            System.err.println("Closing inputfile error.");
        }

    }

}
