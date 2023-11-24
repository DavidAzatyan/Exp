package Modern_Java.Chapter_1_3_Constructor_References;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    //Copy constructor
    public Person(Person p) {
        this.name = p.name;
    }

    public Person(String... names) {
        this.name = Arrays.stream(names)
                .collect(Collectors.joining(" "));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{name:" + name + "}";
    }
}
