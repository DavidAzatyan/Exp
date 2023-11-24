package Modern_Java.Chapter_3_1_Creating_Streams;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        //Creating a stream by Stream.of
        String str = Stream.of("Armenia", "Japan", "Greece", "Spain").collect(Collectors.joining(", "));
        System.out.println(str);

        //Creating a stream by Arrays.stream
        String[] munsters = {"Herman", "Lily", "Eddie", "Marilyn", "Grandpa"};
        str = Arrays.stream(munsters).collect(Collectors.joining(", "));
        System.out.println(str);

        //Creating a stream by Stream.iterate
        List<BigDecimal> nums = Stream.iterate(BigDecimal.TEN, x -> x.add(BigDecimal.TEN)).limit(10).collect(Collectors.toList());
        System.out.println(nums);

        List<String> strings = Stream.iterate("String", x -> x.substring(0, 3)).limit(10).collect(Collectors.toList());
        System.out.println(strings);

        Stream.iterate(LocalDate.now(), localDate -> localDate.plusDays(1))
                .limit(10).collect(Collectors.toList())
                .forEach(System.out::println);

        //Creating a stream by Stream.generate
        Stream.generate(Person::new).limit(10).forEach(person -> {
            person.setName((int)(Math.random()*10));
            System.out.println(person);
        });

        //Creating a stream by Collection.stream
        List<String> bradyBunch = Arrays.asList("Greg", "Marcia", "Peter", "Jan", "Bobby", "Cindy");
        str = bradyBunch.stream().filter(s -> s.length() > 3).collect(Collectors.joining(", "));
        System.out.println(str);

        //Creating a stream by range and rangeClosed
        List<Integer> integers = IntStream.range(10,16)
                .boxed()
                .collect(Collectors.toList());
        System.out.println(integers);
        List<Long> longs = LongStream.rangeClosed(10,16)
                .boxed()
                .collect(Collectors.toList());
        System.out.println(longs);
    }
}
