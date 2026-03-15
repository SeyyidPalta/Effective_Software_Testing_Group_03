package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PlusOneTest {
    @Test
    void plusOne_leadingZero_nok() {
        assertThrows(IllegalArgumentException.class,
                () -> PlusOne.plusOne(new int[]{0, 1, 2}));
    }

    static Stream<Arguments> valueProvider() {
        return Stream.of(
                Arguments.of(new int[]{1}, new int[]{2}),
                Arguments.of(new int[]{9}, new int[]{1, 0}),
                Arguments.of(new int[]{1, 2, 3}, new int[]{1, 2, 4}),
                Arguments.of(new int[]{1, 9, 9}, new int[]{2, 0, 0}),
                Arguments.of(new int[]{9, 9, 9}, new int[]{1, 0, 0, 0})
        );
    }
    @ParameterizedTest
    @MethodSource("valueProvider")
    void plusOne_ok(int[] digits, int[] expected) {
        assertArrayEquals(expected, PlusOne.plusOne(digits));
    }

    static Stream<int[]> negativeValueProvider() {
        return Stream.of(
                new int[]{-1},
                new int[]{1, -1}
        );
    }
    @ParameterizedTest
    @MethodSource("negativeValueProvider")
    void plusOne_negativeValues_nok(int[] digits) {
        assertThrows(IllegalArgumentException.class,
                () -> PlusOne.plusOne(digits));
    }

    @Test
    void plusOne_maxDigitLengthExceeded_nok() {
        int[] digits = new int[101];
        Arrays.fill(digits, 1);
        assertThrows(IllegalArgumentException.class,
                () -> PlusOne.plusOne(digits));
    }

    @Test
    void plusOne_maxDigitLength_ok() {
        int[] digits = new int[100];
        Arrays.fill(digits, 9);

        int[] expected = new int[101];
        Arrays.fill(expected, 0);
        expected[0] = 1;
        assertArrayEquals(expected, PlusOne.plusOne(digits));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void plusOne_nullAndEmptyArray_nok(int[] digits) {
        assertThrows(IllegalArgumentException.class,
                () -> PlusOne.plusOne(digits));
    }

    @Test
    void plusOne_ZeroAsArray_nok() {
        assertThrows(IllegalArgumentException.class,
                () -> PlusOne.plusOne(new int[]{0}));
    }

    static Stream<int[]> greaterNineProvider() {
        return Stream.of(
                new int[]{10},
                new int[]{1, 10}
        );
    }
    @ParameterizedTest
    @MethodSource("greaterNineProvider")
    void plusOne_digitsGreaterThan9_nok(int[] digits) {
        assertThrows(IllegalArgumentException.class,
                () -> PlusOne.plusOne(digits));
    }

    /* added for mutation survivor */

    @Test
    void plusOne_internalZero_ok() {
        assertArrayEquals(new int[]{1, 0, 2}, PlusOne.plusOne(new int[]{1, 0, 1}));
    }
}