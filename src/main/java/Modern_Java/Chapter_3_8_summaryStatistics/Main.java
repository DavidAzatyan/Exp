package Modern_Java.Chapter_3_8_summaryStatistics;

import java.util.LongSummaryStatistics;
import java.util.stream.LongStream;

public class Main {
    public static void main(String[] args) {
        LongSummaryStatistics statistics = LongStream.generate(()-> (int)(Math.random()*100)).limit(1_000_000).summaryStatistics();
        System.out.println(statistics.getMax());
        System.out.println(statistics.getMin());
        System.out.println(statistics.getAverage());
        System.out.println(statistics.getCount());
        System.out.println(statistics.getSum());
        System.out.println(statistics);
    }
}
