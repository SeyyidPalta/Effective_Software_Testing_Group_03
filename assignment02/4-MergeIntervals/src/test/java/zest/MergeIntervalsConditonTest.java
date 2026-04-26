package zest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MergeIntervalsConditonTest {
    private final MergeIntervals mergeIntervals = new MergeIntervals();

    @Test
    void merge_null_nok() {
        assertThrows(IllegalArgumentException.class,
                () -> mergeIntervals.merge(null));
    }

    @Test
    void merge_intervalWithStartGreaterThanEnd_nok() {
        assertThrows(IllegalArgumentException.class,
            () -> mergeIntervals.merge(new int[][]{{1, 3}, {4, 2}}));
    }

    @Test
    void merge_intervalWithTooManyElements_nok() {
        assertThrows(IllegalArgumentException.class,
            () -> mergeIntervals.merge(new int[][]{{1, 3, 5}, {2, 4}}));
    }

    @Test
    void merge_intervalWithTooFewElements_nok() {
        assertThrows(IllegalArgumentException.class,
            () -> mergeIntervals.merge(new int[][]{{1}, {2, 4}}));
    }

    @Test
    void merge_tooManyIntervals_nok() {
        int[][] intervals = new int[10_001][2];
        for (int i = 0; i < 10_001; i++) {
            intervals[i] = new int[]{i, i + 1};
        }

        assertThrows(IllegalArgumentException.class,
                () -> mergeIntervals.merge(intervals));
    }
}
