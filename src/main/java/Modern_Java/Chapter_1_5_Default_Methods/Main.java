package Modern_Java.Chapter_1_5_Default_Methods;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 5, 2, 0, 3);
        boolean removed = list.removeIf(x -> x <= 0);
        System.out.println("Элементы " + (removed ? "были" : "НЕ были") + " удалены");
        list.forEach(System.out::println);
    }
}
