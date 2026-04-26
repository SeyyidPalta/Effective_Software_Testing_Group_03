package zest;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@PropertyDefaults(tries = 100)
public class MergeIntervalsPropertyTest {
    private final MergeIntervals mergeIntervals = new MergeIntervals();

    @Property
    void merge_sortedResults(
            @ForAll @IntRange int start1,
            @ForAll @IntRange int end1,
            @ForAll @IntRange int start2,
            @ForAll @IntRange int end2) {
        Assume.that(start1 <= end1 && start2 <= end2);

        int[][] intervals = {{start1, end1}, {start2, end2}};
        int[][] result = mergeIntervals.merge(intervals);

        for (int i = 0; i < result.length - 1; i++) {
            assertTrue(result[i][0] <= result[i + 1][0],
                "Intervals must be sorted");
        }
    }

    @Property
    void merge_noDuplicateValsInResult(
            @ForAll @IntRange int start1,
            @ForAll @IntRange int end1,
            @ForAll @IntRange int start2,
            @ForAll @IntRange int end2) {
        Assume.that(start1 < end1 && start2 < end2);

        int[][] intervals = {{start1, end1}, {start2, end2}};
        var resAsList = Arrays.stream(mergeIntervals.merge(intervals))
                .flatMapToInt(Arrays::stream)
                .boxed()
                .collect(Collectors.toList());
        assertEquals(new HashSet<>(resAsList).size(), resAsList.size());
    }

    @Property
    void merge_singleIntervalUnchanged(
            @ForAll @IntRange int start,
            @ForAll @IntRange int end) {
        Assume.that(start <= end);

        int[][] intervals = {{start, end}};
        int[][] merged = mergeIntervals.merge(intervals);

        assertEquals(start, merged[0][0], "Start neq input");
        assertEquals(end, merged[0][1], "End neq input");
    }

    @Property
    void merge_completeOverlappingIntervals(
            @ForAll @IntRange int val1,
            @ForAll @IntRange int val2,
            @ForAll @IntRange int val3) {
        Assume.that(val1 <= val2 && val2 <= val3);

        int[][] intervals = {{val1, val3}, {val1, val2}, {val2, val3}};
        int[][] merged = mergeIntervals.merge(intervals);

        assertEquals(1, merged.length);
        assertEquals(val1, merged[0][0]);
        assertEquals(val3, merged[0][1]);
    }

    @Property
    void merge_nonOverlappingIntervals_remainSeparate(
            @ForAll @IntRange(min = 0, max = 2000) int start1,
            @ForAll @IntRange(min = 2001, max = 4000) int end1,
            @ForAll @IntRange(min = 4001, max = 6000) int start2,
            @ForAll @IntRange(min = 6001, max = 8000) int end2) {
        Assume.that(start1 <= end1 && start2 <= end2 && end1 < start2);

        int[][] intervals = {{start1, end1}, {start2, end2}};
        int[][] merged = mergeIntervals.merge(intervals);

        assertEquals(2, merged.length);
        assertEquals(start1, merged[0][0]);
        assertEquals(end1, merged[0][1]);
        assertEquals(start2, merged[1][0]);
        assertEquals(end2, merged[1][1]);
    }

    @Property(tries = 1000)
    void merge_outputSizeLessThanOrEqualToInput(
            @ForAll @IntRange int start1,
            @ForAll @IntRange int end1,
            @ForAll @IntRange int start2,
            @ForAll @IntRange int end2,
            @ForAll @IntRange int start3,
            @ForAll @IntRange int end3) {
        Assume.that(start1 <= end1 && start2 <= end2 && start3 <= end3);

        int[][] intervals = {{start1, end1}, {start2, end2}, {start3, end3}};
        int[][] merged = mergeIntervals.merge(intervals);

        assertTrue(merged.length <= intervals.length);
    }

    @Property
    void merge_emptyArray() {
        assertEquals(0,
                mergeIntervals.merge(new int[][]{}).length);
    }

    @Property
    void merge_resultBoundsAreValid(
            @ForAll @IntRange int start1,
            @ForAll @IntRange int end1,
            @ForAll @IntRange int start2,
            @ForAll @IntRange int end2) {
        Assume.that(start1 <= end1 && start2 <= end2);

        int[][] intervals = {{start1, end1}, {start2, end2}};
        int[][] merged = mergeIntervals.merge(intervals);

        for (int[] interval : merged) {
            assertTrue(interval[0] <= interval[1],
                "result must have: start <= end");
        }
    }

    @Property
    void merge_intervalsNonOverlapping(
            @ForAll @IntRange(min = 0, max = 2500) int start1,
            @ForAll @IntRange(min = 2500, max = 5000) int mid,
            @ForAll @IntRange(min = 5000, max = 7500) int end1,
            @ForAll @IntRange(min = 7500, max = 10_000) int start2,
            @ForAll @IntRange(min = 10_000, max = 12_500) int end2) {
        Assume.that(start1 <= mid && mid <= end1 && start2 <= end2);

        int[][] intervals = {{start1, end1}, {start2, end2}};
        int[][] merged = mergeIntervals.merge(intervals);

        for (int i = 0; i < merged.length; i++) {
            for (int j = i + 1; j < merged.length; j++) {
                assertFalse(merged[i][0] <= merged[j][0] && merged[j][1] <= merged[i][1]);
                assertFalse(merged[j][0] <= merged[i][0] && merged[i][1] <= merged[j][1]);
            }
        }
    }
}
