package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CompareVersionNumbersTest {
    
    @Test
    void testNullInputs() {
        assertThrows(IllegalArgumentException.class, () -> CompareVersionNumbers.compareVersion(null, "1.0"));
        assertThrows(IllegalArgumentException.class, () -> CompareVersionNumbers.compareVersion("1.0", null));
    }

    @ParameterizedTest
    @CsvSource({
        "1.01, 1.001, 0",   // Leading zeros ignored
        "1.0, 1.0.0, 0",    // Trailing zeros ignored
        "0.1, 1.1, -1",     // v1 < v2
        "1.1, 0.1, 1",      // v1 > v2
        "1.2, 1.10, -1",    // Numeric comparison (2 < 10)
        "1.10, 1.2, 1",     // Numeric comparison (10 > 2)
        "1, 1.1, -1",       // v2 has more segments
        "1.1, 1, 1"         // v1 has more segments
    })
    void testCompareVersion(String v1, String v2, int expected) {
        assertEquals(expected, CompareVersionNumbers.compareVersion(v1, v2));
    }

    @Test
    void testSameLengthEqualVersions() {
        // Ensures the loop finishes and returns 0
        assertEquals(0, CompareVersionNumbers.compareVersion("1.1", "1.1"));
    }
}