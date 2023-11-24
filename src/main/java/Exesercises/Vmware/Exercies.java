package Exesercises.Vmware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercies {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>(Arrays.asList("pat", "tap", "apt", "bat"));

        int count = 0;
        for (int i = 0; i < words.size() - 1; i++) {
            char aa[] = words.get(i).toLowerCase().toCharArray();
            for (int j = i + 1; j < words.size() - 1; j++) {
                char bb[] = words.get(j).toLowerCase().toCharArray();

                if (aa.length != bb.length) {
                    System.out.println(false);
                } else {
                    java.util.Arrays.sort(aa);
                    java.util.Arrays.sort(bb);
                    System.out.println(java.util.Arrays.equals(aa, bb));
                    if (java.util.Arrays.equals(aa, bb)) {
                        count++;
                        break;
                    }
                }
            }
        }
        System.out.println(words.size() - count);

    }
}
