# Compare Version Numbers — Specification-Based Testing (7 Steps)

## Problem summary

We need to compare two version strings made of dot-separated numeric revisions. Each revision is interpreted as an integer. The first differing revision decides the order. If one string has fewer revisions, missing segments count as 0.

**Logic:** `-1` if `version1 < version2`, `1` if `version1 > version2`, `0` if equal.

**Constraints:** lengths up to 500; only digits and dots; revisions fit in 32-bit signed `int`.

**Bug in source code:** In line 30 and 32, the symbols '<' and '>' should be inverted to align with the logic.

## 1. Understanding the requirements and outputs

- **Inputs:** `String version1`, `String version2`.
- **Output:** `int` in `{ -1, 0, 1 }`.

## 2. Explore what the program does for various inputs

**README example:** `"1.01"` vs `"1.001"` → both parse to segments `{1, 1}` vs `{1, 1}` → 0.

**Trailing segment count:** `"1.0"` vs `"1.0.0"` → compare `1==1`, `0==0`, then `0` vs nothing → 0.

**Order:** `"0.1"` vs `"1.1"` → first segment `0 < 1` → -1. Swap arguments → 1.

**Multi-digit revisions:** `"1.2"` vs `"1.10"` → second segment 2 < 10 → -1; reversed arguments → 1.

**Padding:** `"1"` vs `"1.1"` → second segment 0 vs 1 → -1. `"1.1"` vs `"1"` → 1.

**Equal same length:** `"1.1"` vs `"1.1"` → loop completes → 0.

**`null`:** `IllegalArgumentException` with message `"Version strings cannot be null"`.


## 3. Explore possible inputs and outputs, and identify partitions

**Input — valid versions:** digits and dots only.

**Input — null:** exception.

**Output — negative:** `version1` strictly less at first differing index.

**Output — positive:** `version1` strictly greater at first differing index.

**Output — zero:** all compared segments equal after zero padding.

**Leading zeros:** same numeric value → still equal.

**Unequal length:** shorter side implicitly extended with `0`.


## 4. Analyze the boundaries

- **Single segment:** `"1"` vs `"1.0"`.
- **Same string twice:** ensures loop runs to end and returns '0'.
- **Difference on first revision** vs **later revision.**
- **Large segment values**: `10` vs `2` in the same position.
- **Null**


## 5. Devise test cases

| Idea | Example | Expected |
|------|----------------------|----------|
| Leading zeros | `"1.01"`, `"1.001"` | 0 |
| Trailing zeros | `"1.0"`, `"1.0.0"` | 0 |
| v1 < v2 (first diff) | `"0.1"`, `"1.1"` | -1 |
| v1 > v2 | `"1.1"`, `"0.1"` | 1 |
| Numeric 2 vs 10 | `"1.2"`, `"1.10"` | -1 |
| Reversed | `"1.10"`, `"1.2"` | 1 |
| Shorter vs longer | `"1"`, `"1.1"` | -1 |
| Longer vs shorter | `"1.1"`, `"1"` | 1 |
| Equal | `"1.1"`, `"1.1"` | 0 |
| Invalid | `null` with valid other | `IllegalArgumentException` |


## 6. Automate the test cases


## 7. Augment the test suite with creativity and experience

A stress testing which has long strings up to 500 characters.

## Structural Tests (JaCoCo)

The line coverage is 95% according to the results on JaCoCo. 
100% of the logic under test is covered; the missing line (line 3) is the unused default constructor.” 


## Mutation Tests (PIT)

Mutation test coverage: 13/14 (line 25), meaning one mutant survived:

Possible reasoning: 

Line 25 `for (int i = 0; i < maxLength; i++)` is red.
 e.g. **`i < maxLength` → `i <= maxLength`**. 
 One extra iteration only compares 0 vs 0; result stays the same → equivalent mutant.
