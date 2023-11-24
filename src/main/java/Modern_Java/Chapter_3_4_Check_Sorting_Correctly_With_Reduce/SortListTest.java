package Modern_Java.Chapter_3_4_Check_Sorting_Correctly_With_Reduce;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class SortListTest {
    @Test
    public void testSort() {
        List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");
        List<String> sorted = strings.stream()
                .sorted((x, y) -> x.length() > y.length() ? 1 : -1)
                .collect(Collectors.toList());
        sorted.stream().reduce((prev, curr) -> {
            assertTrue(prev.length() <= curr.length());
            return curr;
        });
    }
}
