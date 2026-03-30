# Excel Sheet Column Number — Specification-Based Testing (7 Steps)

## Problem summary

We need to map an Excel column title from `A`–`Z` to its 1-based column index. For example, `"A"→1`, …, `"Z"→26`, `"AA"→27`, etc. This is a positional base‑26 scheme where each position contributes `value = 1…26`, not zero-based digits.

Constraints: `1 ≤ columnTitle.length ≤ 7`, only `'A'`…`'Z'`, titles in range up to `"FXSHRXW"` (column value `Integer.MAX_VALUE` for that last title per problem cap).

Implementation: `titleToNumber` rejects ` null`, empty strings, and any character outside `'A'`–`'Z'` with `IllegalArgumentException`.

Bug in the source code: In line 27, I changed 'result = result * 26 + (c - 'A')' to 'result = result * 26 + (c - 'A' + 1),' because if the input is "A", ('A' - 'A') equals 0. The original source code will return 0 instead of 1.

## 1. Understanding the requirements and outputs

- **Input:** `String columnTitle`.
- **Output:** `int` column number.


## 2. Explore what the program does for various inputs

**Single letter:** `"A"` → 1, `"Z"` → 26.

**Two letters:** `"AA"` → 27, `"AZ"` → 52, `"BA"` → 53, `"ZZ"` → 702.

**Three letters:** `"AAA"` → 703.

**README example:** `"ZY"` → 701.

**Maximum in suite:** `"FXSHRXW"` → 2147483647

**`null` / `""`:** throw `"Column title cannot be null or empty"`.

**Invalid character:** e.g. `@` (before `'A'`), `[` (after `'Z'`), lowercase, digit, space, or mixed like `"A1"` — throw `"Invalid character in column title"`.


## 3. Explore possible inputs and outputs, and identify partitions

**Valid partition:** non-empty string, every character in `[A,Z]` — output is the derived integer (within range implied by length cap).

**Invalid inputs:** `null` or empty string, at least one character not in `[A,Z]` (including lowercase, digits, punctuation, spaces).

**Output sub-partitions:** smallest title `"A"`, largest single `"Z"`, first two-letter title `"AA"`, last two-letter `"ZZ"`, carry into three letters `"AAA"`, and **max** title `"FXSHRXW"`.


## 4. Analyze the boundaries

| Boundary | Role |
|----------|------|
| `"A"`, `"Z"` | Single-character min/max values (1 and 26). |
| `"AA"`, `"ZZ"` | Two-character min/max in Excel naming. |
| `"AAA"` | First three-character title after `"ZZ"`. |
| `"ZY"` | Non-trivial middle case. |
| `"FXSHRXW"` | Upper bound of valid column index in problem. |
| `null`, `""` | Guard on input existence. |
| Char `< 'A'`, `> 'Z'`, non-letter | Left and right of valid range + typical invalid classes. |
| `"A1"` | Valid prefix then invalid — exercises sequential validation. |
| `" A"` | Invalid on first character, in this example, the space. |


## 5. Devise test cases

Tests align with the table above:

- **Null and empty** — two throws in one method.
- **Parameterized invalid strings** — `@`, `[`, `a`, `1`, `A1`, ` ` + `A`.
- **Parameterized valid CSV** — `A`…`Z` ends, `AA`, `AZ`, `BA`, `ZY`, `ZZ`, `AAA`, `FXSHRXW`.


## 6. Automate the test cases


## 7. Augment the test suite with creativity and experience

**ASCII neighbors** `@` and `[`** pin the `c < 'A' || c > 'Z'` guard to just outside uppercase letters.

## Structural Tests (JaCoCo)

The line coverage is 9/10. 
100% of the logic under test is covered; the missing line (line 3) is the unused default constructor.” 

## Mutation Tests (PIT)

The mutation test coverage hits 13/13, meaning all mutants are killed.
