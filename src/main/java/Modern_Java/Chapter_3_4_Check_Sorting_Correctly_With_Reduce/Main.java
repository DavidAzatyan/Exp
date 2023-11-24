package Modern_Java.Chapter_3_4_Check_Sorting_Correctly_With_Reduce;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        BigDecimal total = Stream.iterate(BigDecimal.ONE, x -> x.add(BigDecimal.ONE))
                .limit(10)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(total);

        List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");
        Comparator<String> strComparator = (x, y) -> (x.length() > y.length() ? 1 : -1);
        List<String> sorted = strings.stream()
                .sorted(strComparator)
                .collect(Collectors.toList());
        System.out.println(sorted);

        List<String> strings1 = Arrays.asList("this", "is", "a", "list", "of", "strings");
        System.out.println(strings1);
        System.out.println(new SortList<String>().sort(strComparator,strings1));

    }
}
