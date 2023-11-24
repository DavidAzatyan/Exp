package Modern_Java.Chapter_1_2_Method_References;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Example {
    public static void main(String[] args) {

        Consumer<Integer> consumer = (x) -> System.out.print(x + " ");
        Consumer<Integer> consumer1 = System.out::print;
        Stream.of(4, 5, 1, 2, 0, 6, 9).forEach(consumer);
        System.out.println();
        Stream.of(4, 5, 1, 2, 0, 6, 9).forEach(consumer1);
        System.out.println();

        Stream.generate(Math::random).limit(10).forEach(System.out::print);

        System.out.println();

        List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");
        List<String> sorted = strings.stream().sorted((x,y) -> x.compareTo(y)).collect(Collectors.toList());
        List<String> sorted1 = strings.stream().sorted(String::compareTo).collect(Collectors.toList());
        System.out.println(strings);
        System.out.println(sorted);
        System.out.println(sorted1);

        Stream.of("this", "is", "a", "stream", "of", "strings").map(String::length).forEach(System.out::print);
    }
}
