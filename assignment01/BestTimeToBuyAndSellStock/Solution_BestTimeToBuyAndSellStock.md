# Best Time to Buy and Sell Stock — Specification-Based Testing (7 Steps)

## Problem summary

Given an integer array `prices` where `prices[i]` is the price on day `i`, choose one buy day and later one sell day to maximize profit `prices[sell] - prices[buy]`. If no positive profit exists, return 0.

**Constraints:** length `1 ≤ prices.length ≤ 10^5`, values `0 ≤ prices[i] ≤ 10^4`.

**Bug in source code:** line 18, minPrice is initialized to 0 but should be initialized to prices[0].

## 1. Understanding the requirements and outputs

- **Input:** `int[] prices` (non-null, non-empty; rejecting `null` and `length == 0`).
- **Output:** one `int` — maximum achievable profit from a single buy-then-sell pair, or `0`.


## 2. Explore what the program does for various inputs

**Strictly decreasing**: profit is never positive if you must sell after buy.

**Strictly increasing**: buy on day 0, sell on last day.

**All equal**: any buy/sell gives 0 profit → 0.

**Single day**: no second day to sell → 0.

**V-shaped dip then rise** (e.g. `[2, 1, 10]`): buy at 1, sell at 10 → 9.
**Multiple peaks** (e.g. `[1, 10, 1, 10]`): best pair is 9.

**`null` or `[]`:** throws `IllegalArgumentException` with message `"Input array cannot be null or empty"`.


## 3. Explore possible inputs and outputs, and identify partitions

**Input partition — valid arrays:** non-empty, lengths and values within constraints.

**Input partition — invalid:** `null` or empty → exception.

**Output partition — positive profit:** there exist `i < j` with `prices[j] > prices[i]`; result is that maximum difference with minimum before maximum.

**Output partition — zero profit:** prices never rise after the running minimum (decreasing, flat, or single element).

**Behavior partition — update minimum:** some `prices[i]` is less than or equal to the running minimum so far (drops, flats at the minimum).

**Behavior partition-only (else branch):** price strictly above current minimum; compute candidate profit and possibly update `maxProfit`.


## 4. Analyze the boundaries

- **Length 1**
- **Strictly increasing / decreasing / flat**
- **Mixed:** one global valley then peak.
- **Invalid boundary:** `null`, `new int[]{}`.

## 5. Devise test cases

| Idea | Example  | Expected |
|------|----------------------|----------|
| No profit | `[7, 6, 4, 3, 1]` | 0 |
| Full rising | `[1, 2, 3, 4, 5]` | 4 |
| Flat | `[5, 5, 5, 5]` | 0 |
| Two humps | `[1, 10, 1, 10]` | 9 |
| Single element | `[10]` | 0 |
| New min after start | `[2, 1, 10]` | 9 |
| Equal-to-min then rise | `[2, 5, 2, 6]` | 4 |
| Profit ties | `[1, 6, 1, 6]` | 5 |
| Invalid | `null`, `[]` | `IllegalArgumentException` |

---

## 6. Automate the test cases


## 7. Augment the test suite with creativity and experience

- **`[2, 5, 2, 6]`** and **`[1, 6, 1, 6]`**: target equality on comparisons and repeated peaks—useful for mutation testing.


## Structural Tests (JaCoCo line coverage)

The line coverage is 94%. 
100% of the logic under test is covered; the missing line (line 3) is the unused default constructor.” 


## Mutation Tests (PIT)

Result: 8/10 mutants killed.
Two survivors are potentially boundary mutants on:

- `prices[i] <= minPrice` vs `prices[i] < minPrice`
- `profit >= maxProfit` vs `profit > maxProfit`

For this exercise, the changes are equivalent on all integer inputs: when `prices[i] == minPrice`, updating `minPrice` does not change its value; when `profit == maxProfit`, assigning `maxProfit = profit` does not change `maxProfit`. No test can distinguish them as **equivalent mutants**, not as a weak test suite.