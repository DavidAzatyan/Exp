package Modern_Java.Chapter_3_2_Wrapped_Streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        //Transform a stream to list
        List<Integer> collect = Stream.of(4, 2, 3, 5, 1, 0, 3, 9, -65, 6).collect(Collectors.toList());
        System.out.println(collect);

        //Transform a intStream to list
        List<Integer> collect1 = IntStream.of(4, 2, 3, 5, 1, 0, 3, 9, -65, 6)
                .boxed().collect(Collectors.toList());
        System.out.println(collect1);

        //Transform a intStream to list
        List<Integer> collect2 = IntStream.of(4, 2, 3, 5, 1, 0, 3, 9, -65, 6)
                .mapToObj(Integer::valueOf).collect(Collectors.toList());
        System.out.println(collect2);

        //Transform a intStream to list
        List<Integer> ints = IntStream.of(3, 1, 4, 1, 5, 9).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println(ints);

        //Transform a intStream to array
        int[] array = IntStream.of(4,0,2,5,8).toArray();
        Arrays.stream(array).forEach(i -> System.out.print(i + " "));


    }
}
