package zest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidPerfectSquareTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 4, 9, 16, 100, 10_000, 1_000_000, 100_000_000, 2_147_395_600})
    void validPerfectSquare_true_ok(int num) {
        assertTrue(ValidPerfectSquare.isPerfectSquare(num));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 9_999, 99_999, 1_000_001, 300_000_000, Integer.MAX_VALUE})
    void validPerfectSquare_false_ok(int num) {
        assertFalse(ValidPerfectSquare.isPerfectSquare(num));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, Integer.MAX_VALUE + 1})
    void validPerfectSquare_nok(int num) {
        assertThrows(IllegalArgumentException.class,
                () -> ValidPerfectSquare.isPerfectSquare(num));
    }
}