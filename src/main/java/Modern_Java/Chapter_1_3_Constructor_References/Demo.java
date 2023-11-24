package Modern_Java.Chapter_1_3_Constructor_References;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo {
    public static void main(String[] args) {
        Stream.of("David", "Aram", "Harut", "Armen", "Ashot", "Gevorg").map(Person::new).forEach(System.out::println);

        Person before = new Person("David");
        List<Person> people = Stream.of(before).collect(Collectors.toList());
        Person after = people.get(0);
        System.out.println(before == after);
        before.setName("Davo");
        System.out.println(before == after);
        //demonstration copy constructor
        List<Person> persons = Stream.of(before).map(Person::new).collect(Collectors.toList());
        Person after1 = persons.get(0);
        System.out.println(before == after1);

        String names = "David Harut Aram Nare";
        String[] names1 = {"David Azatyan", "Harut", "Aram", "Nare"};
        List<Person> collect = Arrays.stream(names1).map(n -> n.split(" ")).map(Person::new).collect(Collectors.toList());
        List<Person> collect1 = Stream.of(names).map(n -> n.split(" ")).map(Person::new).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(collect1);

        ArrayList<String> arrayNames = new ArrayList<>(Arrays.asList("David","Nare","Artem"));
        Person[] people1 = arrayNames.stream().map(Person::new).toArray(Person[]::new);
        for(Person p : people1){
            System.out.println(p);
        }


    }
}
