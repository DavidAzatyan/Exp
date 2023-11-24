package DataStructure.Set;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 5_000_000; i++) {
//            int r = random.nextInt(5_000_000);
            list.add(i);
        }

        TreeSet<Integer> treeSet = new TreeSet<>();
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            treeSet.add(list.get(i));
        }
        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start1);

        HashSet<Integer> hashSet = new HashSet<>();
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            hashSet.add(list.get(i));
        }
        TreeSet<Integer> treeSet1 = new TreeSet<>(hashSet);
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
    }
}
