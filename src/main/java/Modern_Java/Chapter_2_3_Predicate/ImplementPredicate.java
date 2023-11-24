package Modern_Java.Chapter_2_3_Predicate;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ImplementPredicate {
    public String getNamesOfLength(int length, String... names) {
        return Arrays.stream(names)
                .filter(s -> s.length() == length)
                .collect(Collectors.joining(", "));
    }

    public String getNameStartingWith(String startWith, String... names){
        return Arrays.stream(names)
                .filter(s -> s.startsWith(startWith))
                .collect(Collectors.joining(", "));
    }

    public String getNameSatisfyingCondition(Predicate<String> condition, String... names){
        return Arrays.stream(names)
                .filter(condition)
                .collect(Collectors.joining(", "));
    }
}
