package Modern_Java.Chapter_1_6_Static_Methods_in_Interface;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> bonds = Arrays.asList("Connery", "Lazenby", "Moore", "Dalton", "Brosnan", "Craig");
        List<String> sorted = bonds.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        System.out.println(sorted);
        sorted = bonds.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(sorted);
        sorted = bonds.stream().sorted(Comparator.comparing(String::length)).collect(Collectors.toList());
        System.out.println(sorted);
        sorted = bonds.stream().sorted(Comparator.comparing(x -> x.charAt(x.length() - 1))).collect(Collectors.toList());
        System.out.println(sorted);
        sorted = bonds.stream().sorted(Comparator.comparing(String::toLowerCase)).collect(Collectors.toList());
        System.out.println(sorted);
    }
}
