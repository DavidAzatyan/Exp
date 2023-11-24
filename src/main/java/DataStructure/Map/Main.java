package DataStructure.Map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Student student = new Student("David", 28);
        Student student1 = new Student("Aram", 40);
        Student student2 = new Student("Hayk", 30);
        Student student3 = new Student("Vardan", 90);
        Student student4 = new Student("David", 28);
        Map<Student, Integer> map = new HashMap<>();
        map.put(student, 10);
        map.put(student1, 15);
        map.put(student2, 20);
        map.put(student3, 100);
        map.put(student4, 1005);
        System.out.println(map.get(student3));
        System.out.println(map);

    }
}
