package Exesercises.Vmware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Exercies_1 {

    static boolean isAnagram(String A, String B) {
        if (A.length()!=B.length()){
            return false;
        }
        A = new String(A.toLowerCase().toCharArray());
        B = new String(B.toLowerCase().toCharArray());
        HashMap mapA= new HashMap();
        HashMap mapB= new HashMap();
        boolean anagram = true;
        for (int i = 0; i < A.length(); i++){
            if (mapA.get(A.charAt(i)) == null){
                mapA.put(A.charAt(i),1);
            }
            else{
                mapA.put(A.charAt(i),1 + (int)mapA.get(A.charAt(i)));
            }
            if (mapB.get(B.charAt(i)) == null){
                mapB.put(B.charAt(i),1);
            }
            else{
                mapB.put(B.charAt(i),1 + (int)mapB.get(B.charAt(i)));
            }
        }
        for (Object key : mapA.keySet()) {
            if (mapA.get(key)!=mapB.get(key)){
                anagram = false;
            }
        }
        return anagram;
    }

    public static void main(String[] args) {
        List<String> words = new ArrayList<>(Arrays.asList("inch", "cat", "chin", "act","kit"));
        int count = 0;
        for(int i = 0; i < words.size(); i++){
            for(int j = i + 1; j < words.size(); j++){
                if(isAnagram(words.get(i),words.get(j))){
                    count++;
                    break;
                }
            }
        }
        System.out.println(words.size() - count);
    }
}
