package Exesercises.Exalt.ex1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


//For each words in alist of words,if any two adjacent characters are equal,change one of them.
//Determine the minimum number of substitutions so the final string contains no adjacent equal characters

//Для каждого слова в списке слов, если любые два соседних символа равны, измените один из них.
//Определите минимальное количество замен,
//чтобы окончательная строка не содержала смежных одинаковых символов

public class Ex1 {

//    private static int count_minimum(String s) {
//        int n = s.length();
//        int ans = 0;
//        int i = 0;
//
//        while (i < n) {
//            int j = i;
//            while (j < n && s.charAt(j) == s.charAt(i)) {
//                j++;
//            }
//
//            int diff = j - i;
//
//            ans += diff / 2;
//            i = j;
//        }
//        return ans;
//    }

    static int count_minimum(String word) {
        int len = word.length();
        int result = 0;
        int i = 0;
        while (i < len) {
            int j = i;
            while (j < len && word.charAt(j) == word.charAt(i)) {
                j++;
            }
            result += (j - i) / 2;
            i = j;
        }
        return result;
    }

    static List<Integer> minimalOperations(List<String> words) {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            list.add(count_minimum(words.get(i)));
        }
        return list;

    }

    public static void main(String[] args) {

        List<String> words = new ArrayList<>(Arrays.asList("add", "boook", "break"));

        System.out.println(minimalOperations(words));

    }


}
