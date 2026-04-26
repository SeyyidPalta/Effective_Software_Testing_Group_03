# Solution: Number of Longest Increasing Subsequence

## Task 1: Code Coverage (100% Line Coverage)

- implemented test examples from the README.md
- implemented a normal valid-input test for post-conditions
- implemented simple invalid-input tests for the required contracts

Ignored test coverage for the `main` method, focused on `findNumberOfLIS` which contains the logic.

---

## Task 2: Designing Contracts

1. Not-null array:
   - `nums == null` -> throw `IllegalArgumentException`

2. Array size constraint:
   - Array length must satisfy: `1 <= nums.length <= 2000`

3. Element value range:
   - Each element must satisfy: `-10^6 <= nums[i] <= 10^6`

4. Post-condition:
   - For every valid non-empty input, the returned number of longest increasing subsequences is at least `1`
   - The input array must not be modified

5. Invariant:
   - The class is stateless, so there are no class-level invariants

---

## Task 3: Contract Testing

Tests implemented in `NumberOfLongestIncreasingSubsequenceTest.java` verify:

1. Null input throws `IllegalArgumentException`
2. Empty array throws `IllegalArgumentException`
3. Array length greater than `2000` throws `IllegalArgumentException`
4. Values below `-10^6` throw `IllegalArgumentException`
5. Valid inputs return the expected number of longest increasing subsequences
6. The result is positive for valid input
7. The input array remains unchanged after method execution

---

## Task 4: Property-Based Testing (jqwik)

1. For every generated valid array, the result is positive
2. For arrays where all elements are equal, the number of longest increasing subsequences equals the array length

These properties are simple and directly follow from the problem definition.

---

## Bug Report and Fix

**Bug Found**: The original implementation initialized `lengths` and `counts` with `0`. This caused valid inputs, including the README examples, to return `0` because each element was not counted as a length-1 increasing subsequence.

**Fix**: Initialized both arrays with `1`, because every element is itself an increasing subsequence of length `1` with count `1`.

---

## Generative AI Use

- **Prompt**: "According to this requirement, do the exercise 3-NumberOfLongestIncreasingSubsequence"
- **Explanation**: AI assistance was used to inspect the exercise requirements, identify the dynamic-programming initialization bug, and draft focused JUnit and jqwik tests. The final solution should be reviewed and understood by all team members before submission.
