package zest;

import java.util.Arrays;

public class PlusOne {
    /**
     * Provide a empty private constructor (no tests necessary)
     */
    private PlusOne(){}

    /**
     * Increments a large integer represented as an array of digits.
     *
     * @param digits the array representing the integer
     * @return a new array representing the incremented integer
     * @throws IllegalArgumentException if digits is null or empty
     */
    public static int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty");
        } else if (digits.length > 100) {
            throw new IllegalArgumentException("Maximum digit length exceeded");
        } else if (digits[0] == 0) {
            throw new IllegalArgumentException("Leading zeros are not allowed");
        } else if (Arrays.stream(digits).anyMatch(d -> d < 0)
                || Arrays.stream(digits).anyMatch(d -> d > 9)) {
            throw new IllegalArgumentException("Each digit has to be between 0 and 9");
        }

        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            digits[i] = 0;
        }

        // If we reach here, all digits were 9 (e.g., 999)
        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }

}