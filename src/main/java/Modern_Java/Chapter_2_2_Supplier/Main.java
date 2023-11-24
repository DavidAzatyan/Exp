package Modern_Java.Chapter_2_2_Supplier;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Mal", "Wash", "Kaylee", "Inara",
                "Zoё", "Jayne", "Simon", "River", "Shepherd Book");
        Optional<String> first = names.stream().filter(str -> str.startsWith("S")).findFirst();
        System.out.println(first);
        System.out.println(first.orElse("None"));
        System.out.println(first.orElse(String.
                format("Ничего не найдено в %s", names.stream().
                        collect(Collectors.joining(", ")))));

        System.out.println(first.orElseGet(() -> String.
                format("Ничего не найдено в %s", names.stream().
                        collect(Collectors.joining(", ")))));
    }
}
