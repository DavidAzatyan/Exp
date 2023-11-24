package Modern_Java.Chapter_1_4_Functional_Interface;

@FunctionalInterface
public interface MyFunctionalInterface {
    String myAbstractMethod();

    boolean equals(Object o);

    int hashCode();

    String toString();

    default void myDefaultMethod() {
        System.out.println("Hello");
    }

    static void myStaticMethod(){
        System.out.println("This is static method");
    }
}
