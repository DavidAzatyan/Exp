import java.util.*;

public class P {
    public static void main(String[] args) {
        ArrayList<Integer> head = new ArrayList<>(Arrays.asList(9, 9, 1));

        int n = 2 * head.get(0);

        ArrayList<Integer> array = new ArrayList<>();
        int index = 0;
        if (n > 9) {
            array.add(n / 10);
            array.add(n % 10);
            index = 1;
        }else {
            array.add(n % 10);
        }
        int rest = 0;
        int h = head.size();
        while (h > 1) {
            n = 2 * head.get(head.size() - h + 1) + rest;
            int m = n % 10;
            rest = n / 10;
            if (n > 9) {
                array.set(index, array.get(index) + rest);
                array.add(m);
                rest = 0;
            } else {
                array.add(m);
            }
            index++;
            --h;
        }
        System.out.print(array);

    }

    public static int countHomogenous(String s) {
        int n = s.length();
        char t = s.charAt(0);
        long count = 1;
        int l = 1;
        for (int i = 1; i < n; i++) {
            if (t != s.charAt(i)) {
                t = s.charAt(i);
                l = 1;
            } else {
                t = s.charAt(i);
                l++;
            }
            count += l;
        }
        return (int) (count % 1000000007);
    }
}

