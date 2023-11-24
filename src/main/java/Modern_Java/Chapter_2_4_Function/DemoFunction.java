package Modern_Java.Chapter_2_4_Function;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class DemoFunction {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Mal", "Wash", "Kaylee", "Inara",
                "Zoё", "Jayne", "Simon", "River", "Shepherd Book");

        ToIntFunction<String> function = String::length;
        Function<String, Integer> function1 = s -> s.length();
        Function<String, Integer> function2 = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };
        List<Integer> nameLengths = names.stream().map(function2).collect(Collectors.toList());
        nameLengths = names.stream().map(function1).collect(Collectors.toList());
        int[] ints = names.stream().mapToInt(function).toArray();
        System.out.println(nameLengths);
        Arrays.stream(ints).forEach(i -> System.out.print(i + " "));

    }
}
