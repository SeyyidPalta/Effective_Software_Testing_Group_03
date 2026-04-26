package zest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeIntervalsUnitTest {
    private final MergeIntervals mergeIntervals = new MergeIntervals();

    @Test
    void merge_example1_ok() {
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] expected = {{1, 6}, {8, 10}, {15, 18}};

        assertArrayEquals(expected, mergeIntervals.merge(intervals));
    }

    @Test
    void merge_example2_ok() {
        int[][] intervals = {{1, 4}, {4, 5}};
        int[][] expected = {{1, 5}};

        assertArrayEquals(expected, mergeIntervals.merge(intervals));
    }

    @Test
    void merge_empty_ok() {
        assertArrayEquals(new int[][]{}, mergeIntervals.merge(new int[][]{}));
    }

    @Test
    void merge_oneInterval_ok() {
        int[][] intervals = {{1, 2}};
        int[][] expected = {{1, 2}};

        assertArrayEquals(expected, mergeIntervals.merge(intervals));
    }

    @Test
    void merge_negativeIntervals_ok() {
        int[][] intervals = {{-3, -1}, {-2, 0}, {1, 2}};
        int[][] expected = {{-3, 0}, {1, 2}};

        assertArrayEquals(expected, mergeIntervals.merge(intervals));
    }

    @Test
    void merge_minMaxIntervals_ok() {
        int[][] intervals = {{Integer.MIN_VALUE, Integer.MAX_VALUE}, {2, 3}, {3, 4}};
        int[][] expected = {{Integer.MIN_VALUE, Integer.MAX_VALUE}};

        assertArrayEquals(expected, mergeIntervals.merge(intervals));
    }

    @Test
    void merge_startEqEndIntervals_ok() {
        int[][] intervals = {{-1, -1}, {4, 4}};
        int[][] expected = {{-1, -1}, {4, 4}};

        assertArrayEquals(expected, mergeIntervals.merge(intervals));
    }

    @Test
    void merge_startEqEndFirstInterval_ok() {
        int[][] intervals = {{1, 4}, {4, 4}};
        int[][] expected = {{1, 4}};

        assertArrayEquals(expected, mergeIntervals.merge(intervals));
    }

    @Test
    void merge_intervalEqNext_ok() {
        int[][] intervals = {{1, 3}, {1, 3}};
        int[][] expected = {{1, 3}};

        assertArrayEquals(expected, mergeIntervals.merge(intervals));
    }

    @Test
    void merge_largeFirstInterval_ok() {
        int[][] intervals = {{0, 10_000}, {1, 100}, {2, 1_000}, {3, 9_999}};
        int[][] expected = {{0, 10_000}};

        assertArrayEquals(expected, mergeIntervals.merge(intervals));
    }

    @Test
    void merge_unorderedIntervalsOverlap_ok() {
        int[][] intervals = {{10, 100}, {1, 10}, {1_000, 10_000}, {100, 1_000}};
        int[][] expected = {{1, 10_000}};

        assertArrayEquals(expected, mergeIntervals.merge(intervals));
    }

    @Test
    void merge_unorderedIntervalsNoOverlap_ok() {
        int[][] intervals = {{11, 100}, {1, 10}, {-10, 0}, {1_001, 10_000}, {101, 1_000}};
        int[][] expected = {{-10, 0}, {1, 10}, {11, 100}, {101, 1_000}, {1_001, 10_000}};

        assertArrayEquals(expected, mergeIntervals.merge(intervals));
    }
}
