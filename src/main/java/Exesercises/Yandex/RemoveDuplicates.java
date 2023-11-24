package Exesercises.Yandex;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RemoveDuplicates {
    public static void main(String[] args) {
        int[] matrix = {1, 2, 3,3,2,7};
        Set<Integer> set = new HashSet<>();
        System.out.println(removeDuplicates(matrix));
    }
    static int removeDuplicates(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (Integer n: nums) {
            set.add(n);
        }
        nums = new int[set.size()];
        int i = 0;
        for(Integer n : set){
            nums[i] = n;
            i++;
        }
        return set.size();
    }
}
