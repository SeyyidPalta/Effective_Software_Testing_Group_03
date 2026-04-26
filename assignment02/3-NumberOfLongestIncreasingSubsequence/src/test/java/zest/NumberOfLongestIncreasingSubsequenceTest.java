package zest;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Provide;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberOfLongestIncreasingSubsequenceTest {
    private final NumberOfLongestIncreasingSubsequence solution = new NumberOfLongestIncreasingSubsequence();

    // Structural Testing (Unit Tests)
    @Test
    void exampleWithTwoLongestIncreasingSubsequences() {
        // README example 1 with two LIS results
        assertEquals(2, solution.findNumberOfLIS(new int[]{1, 3, 5, 4, 7}));
    }

    @Test
    void exampleWithOnlyDuplicateValues() {
        // README example 2 where every single element is an LIS
        assertEquals(5, solution.findNumberOfLIS(new int[]{2, 2, 2, 2, 2}));
    }

    @Test
    void normalInputSatisfiesPostConditions() {
        // Postconditions that result is positive and input is unchanged
        int[] nums = {1, 3, 5, 4, 7};
        int[] original = nums.clone();

        int result = solution.findNumberOfLIS(nums);

        assertTrue(result >= 1);
        assertArrayEquals(original, nums);
    }

    @Test
    void nullInputThrowsException() {
        // Precondition that nums must not be null
        assertThrows(IllegalArgumentException.class, () -> solution.findNumberOfLIS(null));
    }

    @Test
    void emptyArrayThrowsException() {
        // Precondition that nums length must be at least 1
        assertThrows(IllegalArgumentException.class, () -> solution.findNumberOfLIS(new int[]{}));
    }

    @Test
    void tooLargeArrayThrowsException() {
        // Precondition that nums length must be at most 2000
        int[] nums = new int[2001];
        Arrays.fill(nums, 1);

        assertThrows(IllegalArgumentException.class, () -> solution.findNumberOfLIS(nums));
    }

    @Test
    void valueOutsideAllowedRangeThrowsException() {
        // Precondition that every value must be within the allowed range
        assertThrows(IllegalArgumentException.class, () -> solution.findNumberOfLIS(new int[]{-1_000_001}));
    }

    @Test
    void valueAboveAllowedRangeThrowsException() {
        // Precondition that values cannot be greater than 1000000
        assertThrows(IllegalArgumentException.class, () -> solution.findNumberOfLIS(new int[]{1_000_001}));
    }

    @Provide
    Arbitrary<int[]> validArrays() {
        return Arbitraries.integers().between(-1_000_000, 1_000_000)
                .array(int[].class)
                .ofMinSize(1)
                .ofMaxSize(20);
    }
}