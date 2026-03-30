package zest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static zest.MoveZeroes.moveZeroes;

public class MoveZeroesTest {
    @Test
    void testBasicFunctionality() {
        int[] actual = {0, 0, 2, 0};
        int[] expected = {2, 0, 0, 0};
        moveZeroes(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testBasicFunctionality2() {
        int[] actual = {0, 1, 0, 3, 12};
        int[] expected = {1, 3, 12, 0, 0};
        moveZeroes(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testNegativeNumbers() {
        int[] actual = {-2, 0, -3, 0, -4, 0, -5};
        int[] expected = {-2, -3, -4, -5, 0, 0, 0};
        moveZeroes(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testNegativeAndPositiveNumbers() {
        int[] actual = {-2, 0, 0, 3, 0, 4, -5};
        int[] expected = {-2, 3, 4, -5, 0, 0, 0};
        moveZeroes(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testInvalidArgument() {
        assertThrows(IllegalArgumentException.class, () -> moveZeroes(null));
    }

    @Test
    void testSingleElement() {
        int[] actual = {0};
        int[] expected = {0};
        moveZeroes(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testOnlyZeroElements() {
        int[] actual = {0, 0, 0, 0};
        int[] expected = {0, 0, 0, 0};
        moveZeroes(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testNonZeroElements() {
        int[] actual = {10, 2, 3, 4, 5, 6, 7, 8, 7};
        int[] expected = {10, 2, 3, 4, 5, 6, 7, 8, 7};
        moveZeroes(actual);
        assertArrayEquals(expected, actual);
    }
}