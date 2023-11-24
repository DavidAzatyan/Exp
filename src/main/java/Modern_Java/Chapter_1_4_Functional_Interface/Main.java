package Modern_Java.Chapter_1_4_Functional_Interface;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        PalindromeChecker palindromeChecker = str -> {
            for (int i = 0; i < str.length() / 2; i++) {
                if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
                    return false;
                }
            }
            return true;
        };


        ArrayList<String> strings = new ArrayList<>(Arrays.asList("abba", "b", "bazar", "aaa", "antar"));
        strings.stream().map(str -> {
            for (int i = 0; i < str.length() / 2; i++) {
                if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
                    return false;
                }
            }
            return true;
        }).forEach(System.out::println);

    }
}