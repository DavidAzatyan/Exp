package Modern_Java.Chapter_3_5_Debugging_Streams_With_Peek;

import org.junit.Test;

import java.util.stream.IntStream;

import static junit.framework.Assert.assertEquals;

public class Main {
    public static void main(String[] args) {

        System.out.println(sumDoublesDivisibleBy3(1, 10));

    }

    public static long sumDoublesDivisibleBy3(int start, int end) {
        return IntStream.rangeClosed(start, end)
                .map(n -> n * 2)
                .filter(n -> n % 3 == 0)
                .reduce(0, (acc, n) -> acc + n);
    }

    @Test
    public void sumDoublesDivisibleBy3() throws Exception {
        assertEquals(1554, sumDoublesDivisibleBy3(100, 120));
    }
}
