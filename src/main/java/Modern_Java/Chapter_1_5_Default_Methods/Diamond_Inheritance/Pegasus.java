package Modern_Java.Chapter_1_5_Default_Methods.Diamond_Inheritance;

public interface Pegasus extends Bird, Horse {
    @Override
    default void speak() {

    }
}
