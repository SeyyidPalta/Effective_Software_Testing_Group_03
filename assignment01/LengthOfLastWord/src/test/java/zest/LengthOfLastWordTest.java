package zest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static zest.LengthOfLastWord.lengthOfLastWord;

public class LengthOfLastWordTest {
    @Test
    void testBasic() {
        assertEquals(5, lengthOfLastWord("Hello World"));
    }

    @Test
    void testTrailingSpaces() {
        assertEquals(5, lengthOfLastWord("Hello World   "));
    }

    @Test
    void testSingleWord() {
        assertEquals(5, lengthOfLastWord("Hello"));
    }

    @Test
    void testMultipleWords() {
        assertEquals(7, lengthOfLastWord("Effective Software Testing"));
    }

    @Test
    void testPeriodEnd() {
        assertEquals(8, lengthOfLastWord("Effective Software Testing."));
    }

    @Test
    void testNull() {
        assertThrows(IllegalArgumentException.class,
                () -> lengthOfLastWord(null));
    }

    @Test
    void testEmpty() {
        assertEquals(0, lengthOfLastWord(""));
    }

    @Test
    void TestSingleLetter() {
        assertEquals(1, lengthOfLastWord("a"));
    }

    @Test
    void TestEmptyStringWithSpace() {
        assertEquals(0, lengthOfLastWord(" "));
    }

    @Test
    void TestSingleLetterWithSpaceBefore() {
        assertEquals(1, lengthOfLastWord(" a"));
    }

    @Test
    void TestSingleLetterWithSpaceAfter() {
        assertEquals(1, lengthOfLastWord("a   "));
    }
}