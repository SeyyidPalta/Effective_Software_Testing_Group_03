package zest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {
    public int[][] merge(int[][] intervals) {
        if (intervals == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        // covered in the constraints but not implemented-> in Unit Test Suite
        if (intervals.length == 0) {
            return intervals;
        }

        if (intervals.length > 10_000) {
            throw new IllegalArgumentException("Input cannot contain more than 10,000 intervals");
        } else if (Arrays.stream(intervals).anyMatch(interval -> interval.length != 2)) {
            throw new IllegalArgumentException("Each interval must contain exactly two integers");
        } else if (Arrays.stream(intervals).anyMatch(interval -> interval[0] > interval[1])) {
            throw new IllegalArgumentException("Each interval start cannot be greater than end");
        }

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> result = new ArrayList<>();
        int[] newInterval = intervals[0];
        result.add(newInterval);

        for (int[] interval : intervals) {
            if (interval[0] <= newInterval[1]) {
                newInterval[1] = Math.max(newInterval[1], interval[1]);
            } else {
                newInterval = interval;
                result.add(newInterval);
            }
        }

        return result.toArray(new int[result.size()][]);
    }
}
