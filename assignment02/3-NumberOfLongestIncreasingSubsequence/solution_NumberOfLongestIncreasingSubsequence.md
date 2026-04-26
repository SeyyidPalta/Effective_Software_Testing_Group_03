# Solution: Number of Longest Increasing Subsequence

## Task 1: Code Coverage (100% Line Coverage)

The test suits have 100% code coverage.

---

## Task 2: Designing Contracts

Preconditions:
   - nums must not be null. If `nums == null` -> throw `IllegalArgumentException`
   - nums.length must be in the range [1, 2000] -> `1 <= nums.length <= 2000`
   - every value must be in the range [-1000000, 1000000] -> `-10^6 <= nums[i] <= 10^6`

Post-condition:
   - For every valid non-empty input, the returned number of longest increasing subsequences is at least `1`
   - The input array must be unchanged

Invariant:
   - The class is stateless, so there are no class-level invariants

---

## Task 3: Contract Testing

1. Null input -> throws `IllegalArgumentException`
2. Empty array -> throws `IllegalArgumentException`
3. Array length greater than `2000` -> throws `IllegalArgumentException`
4. Values below `-10^6` -> throw `IllegalArgumentException`
5. Valid inputs return the expected number of longest increasing subsequences
6. The result is positive for valid input
7. The input array remains unchanged after execution

---

## Task 4: Property-Based Testing (jqwik)

Report:
tries = 1000                  | # of calls to property
checks = 1000                 | # of not rejected calls
generation = RANDOMIZED       | parameters are randomly generated
after-failure = SAMPLE_FIRST  | try previously failed sample, then previous seed
when-fixed-seed = ALLOW       | fixing the random seed is allowed
edge-cases#mode = MIXIN       | edge cases are mixed in
edge-cases#total = 9          | # of all combined edge cases
edge-cases#tried = 9          | # of edge cases tried in current run
seed = -1554704816562621794   | random seed to reproduce generated values

Results:
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0

---

## Bug

The original code initialized `lengths` and `counts` with `0`, but this caused valid inputs. 

Fix: Initialized both arrays with `1`, because every element is an increasing subsequence of length `1` with count `1`.