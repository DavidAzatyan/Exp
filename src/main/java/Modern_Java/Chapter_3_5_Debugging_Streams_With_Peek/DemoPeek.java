package Modern_Java.Chapter_3_5_Debugging_Streams_With_Peek;

import java.util.stream.IntStream;

public class DemoPeek {
    public static void main(String[] args) {
        sumDoublesDivisibleBy3(100, 240);
    }

    public static long sumDoublesDivisibleBy3(int start, int end) {
        return IntStream.rangeClosed(start, end)
                .peek(n -> System.out.printf("original: %d%n", n))
                .map(n -> n * 2)
                .peek(n -> System.out.printf("doubled : %d%n", n))
                .filter(n -> n % 3 == 0)
                .peek(n -> System.out.printf("filtered: %d%n", n))
                .sum();
    }
}
