package Modern_Java.Chapter_3_4_Check_Sorting_Correctly_With_Reduce;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortList<T extends Comparable<T>> {

    public List<T> sort(Comparator<T> comparator, List<T> list) {
        return list.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
