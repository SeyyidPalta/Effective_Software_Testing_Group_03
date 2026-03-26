package zest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindFirstOccurrenceTest {
    @Test
    void TestBothEmpty() {
        int expected = 0;
        int actual = FindFirstOccurrence.strStr("", "");
        assertEquals(expected, actual);
    }

    @Test
    void TestStringNotFound() {
        int expected = -1;
        int actual = FindFirstOccurrence.strStr("lorem", "bob");
        assertEquals(expected, actual);
    }

    @Test
    void TestExactMatch() {
        int expected = 0;
        int actual = FindFirstOccurrence.strStr("bob", "bob");
        assertEquals(expected, actual);
    }

    @Test
    void TestValidStringWithResultEnd() {
        int expected = 3;
        int actual = FindFirstOccurrence.strStr("hi bob", "bob");
        assertEquals(expected, actual);
    }

    @Test
    void TestValidStringNeedleAtLast() {
        int expected = 4;
        int actual = FindFirstOccurrence.strStr("hello", "o");
        assertEquals(expected, actual);
    }

    @Test
    void TestStringAndNeedleInTheMiddle() {
        int expected = 2;
        int actual = FindFirstOccurrence.strStr("hello", "llo");
        assertEquals(expected, actual);
    }

    @Test
    void TestValidStringEmptyNeedle() {
        int expected = 0;
        int actual = FindFirstOccurrence.strStr("Alice", "");
        assertEquals(expected, actual);
    }

    @Test
    void TestInvalidStringEmptyNeedle() {
        assertThrows(IllegalArgumentException.class, () -> FindFirstOccurrence.strStr(null, ""));
    }

    @Test
    void TestValidStringInvalidNeedle() {
        assertThrows(IllegalArgumentException.class, () -> FindFirstOccurrence.strStr("Alice", null));
    }

    @Test
    void TestEmptyNeedle() {
        assertEquals(0, FindFirstOccurrence.strStr("hello", ""));
    }

    @Test
    void TestEmptyHaystack() {
        assertEquals(-1, FindFirstOccurrence.strStr("", "a"));
    }

    @Test
    void TestNeedleLongerThanString() {
        int expected = -1;
        int actual = FindFirstOccurrence.strStr("hi", "hello");
        assertEquals(expected, actual);
    }

    @Test
    void TestRepeatedPatterns() {
        int expected = 0;
        int actual = FindFirstOccurrence.strStr("aaaaaa", "aaa");
        assertEquals(expected, actual);
    }

    @Test
    void TestOverlappingMatches() {
        int expected = 0;
        int actual =  FindFirstOccurrence.strStr("abababab", "ababab");
        assertEquals(expected, actual);
    }

    @Test
    void TestCaseSensitivity() {
        assertEquals(-1, FindFirstOccurrence.strStr("Abc", "abc"));
    }

    @Test
    void TestSpecialCharacters() {
        assertEquals(4, FindFirstOccurrence.strStr("abc$%def", "%d"));
    }

    @Test
    void TestPartialMatch() {
        assertEquals(-1, FindFirstOccurrence.strStr("aaaab", "aaac"));
    }

    @Test
    void TestLongerNeedleWithSubstring() {
        int expected = -1;
        int actual = FindFirstOccurrence.strStr("aaa", "aaabc");
        assertEquals(expected, actual);
    }

    @Test
    void TestSubstringWithNumbers() {
        int expected = 3;
        int actual = FindFirstOccurrence.strStr("3334", "4");
        assertEquals(expected, actual);
    }
}