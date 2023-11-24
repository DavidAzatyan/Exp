package Other.Data_Structure.MyArrayList;

import java.util.ArrayList;

public class DemoMyArrayList {
    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>();
        System.out.println(list);
        list.add("Albert");
        list.add("Arthur");
        list.add("Karen");
        list.add(null);
        list.add("Zara");
        list.add("Sara");
        list.add("Arthur");
        list.add("Nara");
        list.add("Lara");
        list.add("Vera");
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }
        System.out.println(list.size());
        System.out.println(list.capacity());
        String s = ("Arthur");
        System.out.println(list.remove(s));
        System.out.println(list.contains(s));

        MyArrayList<Integer> arr = new MyArrayList<>();
        arr.add(null);

        System.out.println(list);
    }
}
