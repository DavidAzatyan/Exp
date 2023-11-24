package Modern_Java.Chapter_3_8_summaryStatistics;

import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo {
    public static void main(String[] args) {
        Team team1 = new Team(1, "Los Angeles", 245_000_000);
        Team team2 = new Team(2, "Moscow", 145_000_000);
        Team team3 = new Team(3, "Paris", 365_000_000);
        Team team4 = new Team(4, "Brazil", 126_800_000);
        Team team5 = new Team(5, "Barcelona", 756_000_000);
        Stream.of(team1, team2, team3, team4, team5).forEach(System.out::println);

        DoubleSummaryStatistics statistics = Stream.of(team1, team2, team3, team4, team5)
                .mapToDouble(Team::getSalary)
                .collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
        System.out.println(statistics);

        statistics = Stream.of(team1, team2, team3, team4, team5)
                .collect(Collectors.summarizingDouble(Team::getSalary));
        System.out.println(statistics);
    }
}
