package Exesercises.Vmware;

import java.util.*;

public class Fibonachi {

    static int fib(int index) {
        int x = 0;
        int y = 1;
        int z = 0;
        while (index > 0) {
            z = x + y;
            x = y;
            y = z;
            index--;
        }
        return z;
    }

    static int fib1(int index) {
        int x = 0;
        int y = 1;
        while (index > 0) {
            x = y + x;
            y = x - y;
            x = x - y;
            y = x + y;
            index--;
        }
        return y;
    }

    static int fibRec(int index) {

        if (index == 1 || index == 0) {
            return index;
        }
        return fibRec(index - 1) + fibRec(index - 2);
    }

    static Map<Integer, Integer> map = new HashMap();

    static int fibRecOpt(int index) {
        if (index == 1 || index == 0) {
            return index;
        }
        if (map.containsKey(index)) {
            return map.get(index);
        }
        int num = fibRec(index - 1) + fibRec(index - 2);
        map.put(index, num);
        return num;
    }


    public static void main(String[] args) {

//        Set<Integer> set = new HashSet<>(Arrays.asList(4,7,8,9,2));
//
//        Iterator<Integer> iterator = set.iterator();
//
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

        Map<String,Integer> map = new HashMap<>();
        System.out.println("Aa".hashCode());
        System.out.println("BB".hashCode());
        map.put("Aa",5);
        map.put("BB",6);
        map.put("AA",8);
        System.out.println(map.get("BB"));

//        System.out.println(fib(4));
//        System.out.println(fib1(4));
//        System.out.println(fibRec(4));
//        System.out.println(fibRecOpt(4));
    }
}
