package Proposal2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Separator {

    public static final String SEPARATORS = "-/_.,";
    public static final String DEFAULT_READ_SEPARATORS = ".";
    public static final Pattern SEP = Pattern.compile("[" + SEPARATORS + "]");
    public static final Pattern NOT_SEP = Pattern.compile("[^" + SEPARATORS + "]");
    public static final Pattern SEP_OR_END = Pattern.compile("[" + SEPARATORS + "]|$");

    public static List<String> splitString(String input) {
        List<String> split = new ArrayList<>();
        Matcher matcher = SEP_OR_END.matcher(input);
        int current = 0;
        while (matcher.find()) {
            int end = matcher.end();
            split.add(input.substring(current, end));
            current = end;
        }
        return split;
    }
}
