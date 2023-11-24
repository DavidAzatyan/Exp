package Modern_Java.Chapter_3_1_Creating_Streams;

public class Person {
    private int age;

    public void setName(int age) {
        this.age = age;
    }

    public int getName() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                '}';
    }
}
