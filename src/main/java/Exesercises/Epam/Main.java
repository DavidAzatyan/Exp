package Exesercises.Epam;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>(Arrays.asList("aaa", "bbb", "ccc", "bbb", "aaa", "ddd", "xxx", "dd", "xxx", "zzz", "ccc", "aaa", "ccc"));
        Map<String, Integer> map = new HashMap<>();
        for (String s : strings) {
            if (!map.containsKey(s)) {
                int i = 1;
                map.put(s, i);
            } else {
                Integer n = map.get(s) + 1;
                map.put(s, n);
            }
        }
        for (String s : map.keySet()) {
            if (map.get(s) == 1) {
                System.out.print(s + " ");
            }
        }
        System.out.println(map);
    }
}
