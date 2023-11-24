package Modern_Java.Chapter_3_3_Reduction_Operations;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String[] strings = "this is an enormous array of strings".split(" ");
        Arrays.stream(strings).mapToInt(String::length).forEach(x -> System.out.print(x + " "));
        System.out.println();
        //operations reduction for IntStream
        long count = Arrays.stream(strings).mapToInt(String::length).count();
        System.out.println(count);

        long sum = Arrays.stream(strings).mapToInt(String::length).sum();
        System.out.println(sum);

        OptionalDouble ave = Arrays.stream(strings).mapToInt(String::length).average();
        System.out.println(ave.getAsDouble());

        //TODO to understand how max method work for IntStream and for Stream
        OptionalInt max = Arrays.stream(strings).mapToInt(String::length).max();
        System.out.println(max);

        Optional<Integer> MAX = Arrays.stream(strings)
                .map(String::length)
                .reduce((a, b) -> (a >= b) ? a : b);
        System.out.println(MAX);

        Optional<Integer> MAX1 = Arrays.stream(strings)
                .map(String::length)
                .max((a, b) -> (a < b) ? -1 : ((a == b) ? 0 : 1));
        System.out.println(MAX1);

        Optional<Integer> MAX2 = Arrays.stream(strings).map(String::length).max(Integer::compareTo);
        System.out.println(MAX2);

        OptionalInt min = Arrays.stream(strings).mapToInt(String::length).min();
        System.out.println(min);



        int sum1 = IntStream.rangeClosed(1, 10)
                .reduce((x, y) -> {
                    System.out.printf("x = %d, y = %d%n", x, y);
                    return x + y;
                })
                .orElse(0);
        System.out.println(sum1);

        //Multiple each element by 2
        int mult = IntStream.rangeClosed(1, 10)
                .reduce((x, y) -> {
                    System.out.printf("x = %d, y = %d%n", x, y);
                    return x + 2 * y; // naive attempt
                })
                .orElse(0);
        System.out.println(mult);

        int mult2 = IntStream.rangeClosed(1, 10)
                .reduce(0, (x, y) -> {
                    System.out.printf("x = %d, y = %d%n", x, y);
                    return x + 2 * y; // naive attempt
                });
        System.out.println(mult2);

        Integer maximum = Stream.of(3, 1, 4, 1, 5, 9).reduce(Integer.MIN_VALUE, Integer::max);
        System.out.println("Max value is " + maximum);

        String s1 = Stream.of("Andranik", "Zavik", "Karen", "Ruben", "Shirak")
                .reduce("", String::concat);
        System.out.println(s1);

        String s2 = Stream.of("Andranik", "Zavik", "Karen", "Ruben", "Shirak")
                .collect(() -> new StringBuilder(), (sb, str) -> sb.append(str), (sb1, sb2) -> sb1.append(sb2))
                .toString();
        System.out.println(s2);

        String s3 = Stream.of("Andranik", "Zavik", "Karen", "Ruben", "Shirak")
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        System.out.println(s3);

        String s4 = Stream.of("Andranik", "Zavik", "Karen", "Ruben", "Shirak")
                .collect(Collectors.joining());
        System.out.println(s4);
    }
}
