package Modern_Java.Chapter_1_5_Default_Methods.Diamond_Inheritance;

public interface Horse extends Animal{
    @Override
    default void speak() {
        System.out.println("hihoho");
    }
}
