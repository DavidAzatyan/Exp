package Modern_Java.Chapter_3_6_Converting_Strings_To_Streams_And_Vice_Versa;

import java.util.stream.Stream;

public class PalindromeEvaluator {
    public static void main(String[] args) {
        System.out.println(isPalindrome("abcba"));
        System.out.println(isPalindrome1("aBcba"));
    }

    public static boolean isPalindrome(String str) {
        StringBuilder sb = new StringBuilder();
        for (char s : str.toCharArray()) {
            if (Character.isLetterOrDigit(s)) {
                sb.append(s);
            }
        }
        String forward = str.toLowerCase();
        String backward = sb.reverse().toString().toLowerCase();
        return backward.equals(forward);
    }

    public static boolean isPalindrome1(String str) {
        String forward = str.toLowerCase()
                .codePoints()
                .filter(Character::isLetterOrDigit)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString(); // TODO to understand collect method with three arguments
        String backward = str.toLowerCase();
        return forward.equals(backward);
    }
}
