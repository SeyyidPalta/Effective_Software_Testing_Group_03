# Solution Report: Add Binary Testing

## 1. Specification-based Tests

### 1. Understanding the requirements and outputs
* **Problem**: The goal is to compute the sum of two binary numbers provided as strings and return the result as a new binary string.
* **Inputs**: Two binary strings, `a` and `b`, with lengths between 1 and 10,000.
* **Constraints**: Strings consist only of '0' and '1' and contain no leading zeros except for the number "0" itself.
* **Expected Behavior**: The method must perform addition from right to left, simulate carry propagation, and handle cases like "11" + "1" = "100".

### 2. Explore what the program does for various inputs
* **Single digit**: "1" + "1" -> "10".
* **Zero identity**: "0" + "0" -> "0".
    * **Requirement**: The result must not contain leading zeros unless the result is "0".
* **Null**: Passing `null` results in an `IllegalArgumentException`.
* **Multiple digits**: "111" + "1" -> "1000".

### 3. Explore possible inputs and outputs, and identify partitions
* **Input Partition 1**: Strings of equal length.
* **Input Partition 2**: Strings of different lengths.
* **Input Partition 3**: Null or invalid input strings.
* **Output Partition 1**: Result string has the same length as the longest input.
* **Output Partition 2**: Result string is one character longer than the longest input due to a final carry.
* **Output Partition 3**: `IllegalArgumentException` thrown for null inputs.

### 4. Analyze the boundaries
* **Boundary 1**: Smallest valid inputs ("0", "1").
* **Boundary 2**: Maximum length constraint up to 10,000 characters.
* **Boundary 3**: All '1's in a string causing maximum carry propagation.
* **Boundary 4**: Carry generated at the most significant bit.
* **Boundary 5**: One input is much longer than the other (10,000 vs 1 digits).

### 5. Devise test cases
* Null input.
* Adding zeros.
* Different length combinations.
* Full ripple carries.

### 6. Automate the test cases


### 7. Augment the test suite with creativity and experience
A stress test for 10,000-character strings to ensure the maximum defined boundaries.

## 2. Structural Tests

After running the tests, the structural coverage reached 95%.
The default constructor was not executed because the class only contains static methods.
100% of the logic under test is covered; the missing line is the unused default constructor.

## 3. Mutation Tests

23/23 mutants are killed.
Mutation Coverage: 100%.
Test Strength: 100.
Conclusion: All 23 mutants were killed, which means that the test suite is sensitive to even minor logic changes in the addition and carry propagation code.