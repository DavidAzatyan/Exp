package Modern_Java.Chapter_2_1_Consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class DemoConsumer {
    public static void main(String[] args) {
        Consumer<Integer> consumer = new Consumer<>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        };
        List<Integer> integers = new ArrayList<>(Arrays.asList(4,5,2,0,63,3));
        integers.forEach(consumer);
        integers.forEach(x-> System.out.println(x));
        integers.forEach(System.out::println);

        Optional.of(9).ifPresent(consumer);
    }
}
