package DataStructure.Set;

import java.util.HashSet;
import java.util.TreeSet;

public class Main1 {
    public static void main(String[] args) {
        Student student = new Student("Ando", 45);
        Student student1 = new Student("Areg", 25);
        Student student2 = new Student("Narek", 35);
        Student student3 = new Student("Khachik", 35);
        Student student4 = new Student("Khachik", 35);
        TreeSet<Student> hashSet = new TreeSet<>(new StudentComparatorUsingAge());
        hashSet.add(student);
        hashSet.add(student1);
        hashSet.add(student2);
        hashSet.add(student3);
        hashSet.add(student4);
        System.out.println(student3.equals(student4));
        System.out.println(hashSet);
    }
}
