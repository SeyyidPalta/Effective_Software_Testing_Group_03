package zest;

import java.util.Arrays;

public class NumberOfLongestIncreasingSubsequence {
    // Constraints from the README
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 2000;
    private static final int MIN_VALUE = -1_000_000;
    private static final int MAX_VALUE = 1_000_000;

    public int findNumberOfLIS(int[] nums) {
        validateInput(nums);

        int n = nums.length;
        int[] lengths = new int[n];
        int[] counts = new int[n];
        Arrays.fill(lengths, 1); // bug fixed: initialized with 1 instead of 0
        Arrays.fill(counts, 1); // bug fixed: initialized with 1 instead of 0

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    if (lengths[j] + 1 > lengths[i]) {
                        lengths[i] = lengths[j] + 1;
                        counts[i] = counts[j];
                    } else if (lengths[j] + 1 == lengths[i]) {
                        counts[i] += counts[j];
                    }
                }
            }
        }

        int maxLength = 0;
        for (int length : lengths) {
            if (length > maxLength) {
                maxLength = length;
            }
        }

        int result = 0;
        for (int i = 0; i < n; i++) {
            if (lengths[i] == maxLength) {
                result += counts[i];
            }
        }

        return result;
    }

    private void validateInput(int[] nums) {
        // Preconditions
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }

        if (nums.length < MIN_LENGTH || nums.length > MAX_LENGTH) {
            throw new IllegalArgumentException("Input array length must be in the range [1, 2000]");
        }

        for (int num : nums) {
            if (num < MIN_VALUE || num > MAX_VALUE) {
                throw new IllegalArgumentException("Input values must be in the range [-1000000, 1000000]");
            }
        }
    }
}