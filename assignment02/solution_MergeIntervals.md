# Solution: Merge Intervals

## Task 1: Code Coverage (100% Line Coverage)

- implemented test examples from the README.md
- implemented empty intervals test (should return empty list) 
  - Bug fix: added guard clause for empty input list in `merge` method after null check
- implemented test for only one interval
- implemented test for negative intervals
- implemented test for MIN and MAX integer values merge every other interval
- implemented test start == end in 2 intervals
- implemented test end of one interval is the start of another interval which also ends with same value
- implemented test where start equals start of another interval and end equals end of interval
- implemented test where first interval fully contains all other intervals (merge all in first interval)
- implemented unordered overlapping intervals test (should sort in first step and then merge)
- implemented unordered non-overlapping intervals test (should sort in first step and then merge)

Ignored test coverage for main method, focused on `merge` method which contains the logic.

---

## Task 2: Designing Contracts

1. Not-null array:
    - `intervals == null` -> throw `IllegalArgumentException`

2. Intervals length is greater then 10_000:
    - `intervals.length > 10_000` -> throw `IllegalArgumentException`

3. Each interval must contain exactly two integers:
    - `interval.length != 2` -> throw `IllegalArgumentException`

4. Each interval start cannot be greater than end:
    - `interval[0] > interval[1]` -> throw `IllegalArgumentException`

## Task 3: Contract Testing

Tests implemented in `MergeIntervalsConditonTest.java` to verify and ensure contract enforcement

---

## Task 4: Property-Based Testing (jqwik)

1. Result intervals are sorted
2. Result intervals have no duplicate values
3. Single input interval remains unchanged
4. Completly overlapping intervals results in a single interval
5. Non-overlapping intervals remain unchanged
6. Result intervals size is smaller equals the input intervals size
   - merge_outputSizeLessThanOrEqualToInput: increased the number of intervals to 1000 to better test this property because of generating "false" values
7. Empty input results in empty output
8. Each result interval start is less than or equal to end
9. Result intervals are non-overlapping (end of one interval is less than start of next interval)