package zest;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static zest.LongestCommonPrefix.longestCommonPrefix;

public class LongestCommonPrefixTest {
    @Test
    void testBasicFunctionality() {
        String expected = "fl";
        String actual = longestCommonPrefix(new String[]{"flower", "flow", "flight"});
        assertEquals(expected, actual);
    }

    @Test
    void testArrayWithOneString() {
        String expected = "flower";
        String actual = longestCommonPrefix(new String[]{"flower"});
        assertEquals(expected, actual);
    }

    @Test
    void testArrayWithEmptyString() {
        String expected = "";
        String actual = longestCommonPrefix(new String[]{"", "", ""});
        assertEquals(expected, actual);
    }

    @Test
    void testEmptyString() {
        String expected = "";
        String actual = longestCommonPrefix(new String[]{});
        assertEquals(expected, actual);
    }

    @Test
    void testEmptyStringAndNormalString() {
        String expected = "";
        String actual = longestCommonPrefix(new String[]{"aa", ""});
        assertEquals(expected, actual);
    }

    @Test
    void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> longestCommonPrefix(null));
    }

    @Test
    void testDifferentLengths() {
        String expected = "hel";
        String actual = longestCommonPrefix(new String[]{"hel", "hello", "helsinki"});
        assertEquals(expected, actual);
    }

    @Test
    void testIdenticalStrings() {
        String expected = "effective";
        String actual = longestCommonPrefix(new String[]{"effective", "effective", "effective"});
        assertEquals(expected, actual);
    }

    @Test
    void testNoCommonPrefix() {
        String expected = "";
        String actual = longestCommonPrefix(new String[]{"something", "nothing", "existing"});
        assertEquals(expected, actual);
    }

    @Test
    void testCaseSensitiveness() {
        String expected = "some";
        String actual = longestCommonPrefix(new String[]{"something", "someTHING"});
    }
}