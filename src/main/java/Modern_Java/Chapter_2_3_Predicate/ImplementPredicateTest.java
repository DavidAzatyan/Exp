package Modern_Java.Chapter_2_3_Predicate;

import org.junit.Before;
import org.junit.Test;


import java.util.function.Predicate;
import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;


public class ImplementPredicateTest {
    private ImplementPredicate demo = new ImplementPredicate();
    private String[] names;
    private static final Predicate<String> LENGTH_FIVE = s -> s.length() == 5;
    private static final Predicate<String> STARTS_WITH_S = s -> s.startsWith("S");

    @Before
    public void setUp() {
        names = Stream.of("Mal", "Wash", "Kaylee", "Inara", "Zoё",
                "Jayne", "Simon", "River", "Shepherd Book").sorted()
                .toArray(String[]::new);
    }

    @Test
    public void getNamesOfLength5() {
        assertEquals("Inara, Jayne, River, Simon",
                demo.getNamesOfLength(5, names));
    }

    @Test
    public void getNameStringWith() {
        assertEquals("Shepherd Book, Simon", demo.getNameStartingWith("S", names));
    }

    @Test
    public void getNamesSatisfyingCondition() {
        assertEquals("Inara, Jayne, River, Simon",
                demo.getNameSatisfyingCondition(s -> s.length() == 5, names));
        assertEquals("Shepherd Book, Simon",
                demo.getNameSatisfyingCondition(s -> s.startsWith("S"),
                        names));
        assertEquals("Inara, Jayne, River, Simon",
                demo.getNameSatisfyingCondition(LENGTH_FIVE, names));
        assertEquals("Shepherd Book, Simon",
                demo.getNameSatisfyingCondition(STARTS_WITH_S, names));
    }

    @Test
    public void composedPredicate() {
        assertEquals("Simon",
                demo.getNameSatisfyingCondition(LENGTH_FIVE.and(STARTS_WITH_S), names));
        assertEquals("Inara, Jayne, River, Shepherd Book, Simon", demo.getNameSatisfyingCondition(
                LENGTH_FIVE.or(STARTS_WITH_S), names));
        assertEquals("Kaylee, Mal, Shepherd Book, Wash, Zoё",
                demo.getNameSatisfyingCondition(LENGTH_FIVE.negate(), names));
    }
}

