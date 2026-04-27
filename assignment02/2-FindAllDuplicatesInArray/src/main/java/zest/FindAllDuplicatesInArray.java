package zest;

import java.util.ArrayList;
import java.util.List;

import java.util.HashSet;
import java.util.stream.IntStream;

public class FindAllDuplicatesInArray {
    public List<Integer> findDuplicates(int[] nums) {

        if (nums == null || nums.length == 0 || nums.length > Math.pow(10, 5)) {
            throw new IllegalArgumentException();
        }

        // The second part of this if statement will always return false if it's met but anyway, here we are.
        if (IntStream.of(nums).anyMatch(i -> i < 1) || IntStream.of(nums).anyMatch(i -> i > Integer.MAX_VALUE)) {
            throw new IllegalArgumentException();
        }

        List<Integer> duplicates = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;
            if (nums[index] < 0) {
                duplicates.add(Math.abs(nums[i]));
            } else {
                nums[index] = -nums[index];
            }
        }

        if (hasDuplicates(duplicates)) {
            throw new IllegalArgumentException("There are elements that occur more than twice in the original array.");
        }

        return duplicates;
    }

    // Does the business in O(n), best method so far in terms of space and time complexity
    public static boolean hasDuplicates(List<Integer> arr) {
        HashSet<Integer> set = new HashSet<>();

        for (int num : arr) {
            if (set.contains(num)) {
                return true; // duplicate found
            }
            set.add(num);
        }
        return false;
    }
}
