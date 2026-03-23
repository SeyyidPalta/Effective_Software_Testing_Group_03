package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SortColorsTest {

    static Stream<Arguments> okValueProvider() {
        return Stream.of(
                Arguments.of(new int[]{2, 0, 2, 1, 1, 0}, new int[]{0, 0, 1, 1, 2, 2}),
                Arguments.of(new int[]{0}, new int[]{0}),
                Arguments.of(new int[]{2}, new int[]{2}),
                Arguments.of(new int[]{0, 0, 0}, new int[]{0, 0, 0}),
                Arguments.of(new int[]{0, 1, 2}, new int[]{0, 1, 2}),
                Arguments.of(new int[]{0, 2, 0, 2, 0}, new int[]{0, 0, 0, 2, 2})
        );
    }
    @ParameterizedTest
    @MethodSource("okValueProvider")
    void sortColors_ok(int[] nums, int[] expected) {
        SortColors.sortColors(nums);
        assertArrayEquals(expected, nums);
    }


    static Stream<int[]> nokValueProvider() {
        return Stream.of(
                null,
                new int[]{},
                new int[]{3},
                new int[]{-1},
                new int[]{3, 0, 1},
                new int[]{-1, 0, 1},
                new int[]{1, 3},
                new int[]{}
        );
    }
    @ParameterizedTest
    @MethodSource("nokValueProvider")
    void sortColors_nok(int[] nums) {
        assertThrows(IllegalArgumentException.class, () -> SortColors.sortColors(nums));
    }

    @Test
    void sortColors_exceedMaxLength_nok() {
        int[] nums = new int[301];
        Arrays.fill(nums, 0);
        nums[0] = 1;
        assertThrows(IllegalArgumentException.class, () -> SortColors.sortColors(nums));
    }

    @Test
    void sortColors_maxLength_ok() {
        int[] nums = new int[300];
        Arrays.fill(nums, 0);
        nums[0] = 1;

        int[] expected = new int[300];
        Arrays.fill(expected, 0);
        expected[299] = 1;

        SortColors.sortColors(nums);
        assertArrayEquals(expected, nums);
    }
}