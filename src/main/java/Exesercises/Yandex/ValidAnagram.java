package Exesercises.Yandex;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValidAnagram {
    public static void main(String[] args) {
        String s = "dsfsd";
        String t = "fsdds";
        System.out.println(isAnagram(s, t));
    }

    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Set<String> first = new HashSet<>(Arrays.asList(s.split("")));
        int count = 0;
        int count1 = 0;
        for (String str : first) {
            for (int i = 0; i < s.length(); i++) {
                if (str.equals(String.valueOf(s.charAt(i)))) {
                    count++;
                }
            }
            for (int i = 0; i < t.length(); i++) {
                if (str.equals(String.valueOf(t.charAt(i)))) {
                    count1++;
                }
            }
            if (count != count1) {
                return false;
            }
        }
        return true;
    }
}
