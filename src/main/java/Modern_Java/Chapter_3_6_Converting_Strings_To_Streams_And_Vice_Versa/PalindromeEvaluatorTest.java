package Modern_Java.Chapter_3_6_Converting_Strings_To_Streams_And_Vice_Versa;

import org.junit.Test;

import java.util.stream.Stream;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class PalindromeEvaluatorTest {
    @Test
    public void testIsPalindrome() {
        assertTrue(Stream.of("Madam")
                .allMatch(PalindromeEvaluator::isPalindrome1));
        assertFalse(PalindromeEvaluator.isPalindrome1("Это НЕ палиндром"));
    }
}
