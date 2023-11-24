package Modern_Java.Chapter_1_5_Default_Methods;

/** Java 8 added default methods to maintain backwards compatibility **/

public interface Employee {
    String getFirst();

    String getLast();

    void convertCaffeineToCodeForMoney();

    default String getName() {
        return String.format("%s %s", getFirst(), getLast());
    }
}
