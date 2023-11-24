package Modern_Java.Chapter_2_3_Predicate;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DemoPredicate {
    public static void main(String[] args) {
        String[] names = {"David", "Harutyun", "Sargis", "Aram", "Khachik", "Hovhannes", "Ara", "Mushegh", "Mher", "Eduard", "Suren"};
        System.out.println(getNamesOfLength(3, names));
        System.out.println(getNameStartingWith(names));
        System.out.println(getNameSatisfyingCondition(s -> s.length() == 5, names));
    }

    public static String getNamesOfLength(int length, String... names) {
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String str) {
                return str.length() == length;
            }
        };
        return Arrays.stream(names)
                .filter(predicate)
                .collect(Collectors.joining(", "));
    }

    public static String getNameStartingWith(String... names){
        return Arrays.stream(names)
                .filter(s -> s.startsWith("H"))
                .collect(Collectors.joining(", "));
    }

    public static String getNameSatisfyingCondition(Predicate<String> condition, String... names){
        return Arrays.stream(names)
                .filter(condition)
                .collect(Collectors.joining(", "));
    }
}
