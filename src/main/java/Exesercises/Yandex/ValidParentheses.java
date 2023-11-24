package Exesercises.Yandex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidParentheses {
    public static void main(String[] args) {
        String s = "{}([])[{}]";
        List<String> list = new ArrayList<>(Arrays.asList(s.split("")));
        for (int i = list.size() - 1; i >= 0; i--) {
            String el = list.get(i);
            if(list.get(list.size() - 1).equals("[") || list.get(list.size() - 1).equals("{") || list.get(list.size() - 1).equals("(")){
                break;
            }
            if (el.equals("[") || el.equals("(") || el.equals("{")) {
                int index = i + 1;
                if ((list.get(index).charAt(0) - list.get(i).charAt(0)) == 2 || (list.get(index).charAt(0) - list.get(i).charAt(0)) == 1) {
                    list.remove(index);
                    list.remove(i);
                }
            }
        }
        System.out.println(list.isEmpty());
    }
}
