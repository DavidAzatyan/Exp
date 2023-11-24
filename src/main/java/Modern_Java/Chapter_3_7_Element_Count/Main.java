package Modern_Java.Chapter_3_7_Element_Count;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        long l = Stream.of(1, 3, 5, 2, 4, 6, 8, 10, 7, 9).count();
        System.out.printf("count of elements in the stream is %d\n", l);
        long l1 = Stream.of(1, 3, 5, 2, 4, 6, 8, 10, 7, 9, 11, 13, 15, 12, 14).collect(Collectors.counting());
        System.out.printf("count of elements in the stream is %d\n", l1);
        long l2 = Stream.of(1, 3, 5, 2, 4, 6, 8, 10, 7, 9).mapToLong(x -> 1).sum();
        System.out.printf("count of elements in the stream is %d\n", l2);
        List<String> list = Stream.of("David", "Hrachya", "Manvel", "Minas", "Vardan").collect(Collectors.toList());
        list.forEach(x -> System.out.print(x + " "));
        System.out.println();
        Map<Boolean, Long> numberLengthMap = list.stream().collect(Collectors.partitioningBy(s -> s.length() % 2 == 0, Collectors.counting()));
        numberLengthMap.forEach((k, v) -> System.out.println(k + " : " + v));
    }
}
