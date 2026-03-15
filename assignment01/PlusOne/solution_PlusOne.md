# 1. Specification-based Tests

## 1. Understanding the requirements and outputs
Read the problem description carefully to understand the requirements and expected inputs / outputs

## 2. Explore what the program does for various inputs
- What happens if the input array has only one digit: [1], [9]?
  - Result: [2], [0]
  - Bug: 0 instead of 10
  - Fix: Add a carry to the next digit if the current digit is 9
- What happens if the input array has only zero as digit: [0]?
  - Result: [1]
  - Bug: IllegalArgumentException("Input array cannot contain leading zeroes")
  - Fix: Set pre-condition that the digits array does not contain leading zeroes.
- What happens if the input array is empty: []?
  - Result: IllegalArgumentException("Input array cannot be null or empty")
- What happens if the input array is null?
  - Result: IllegalArgumentException("Input array cannot be null or empty")
- What happens if the input array has multiple digits: [1,2,3], [1,9,9]?
  - Result: [1, 2, 4], [2, 0, 0]

## 3. Explore possible inputs and outputs, and identify partitions
- Input Partition 1: 1 ≤ digits.length ≤ 100 
- Input Partition 2: 0 ≤ digits[i] ≤ 9 and array does not contain leading zeroes
- Output Partition 1: array has 1 digit more than the input array if all digits are 9
- Output Partition 2: array has the same number of digits as the input array if not all digits are 9
- Output Partition 3: array throws IllegalArgumentException if the input array is null or empty
- Output Partition 4: array throws IllegalArgumentException if the input array contains leading zeroes
- Output Partition 5: array throws IllegalArgumentException if the input array contains negative digits or digits greater than 9
- Output Partition 6: array throws IllegalArgumentException if the input array has more than 100 digits

## 4. Analyze the boundaries
- Boundary 1: [0], [1]
- Boundary 2: 100 digits [], 101 digits []
- Boundary 3: [-1], [0]
- Boundary 4: [9], [10]
- Boundary 5: [9] -> [1, 0]
- Boundary 6: [1] -> [2]
- Boundary 7: [] -> IllegalArgumentException("Input array cannot be null or empty")
- Boundary 8: null -> IllegalArgumentException("Input array cannot be null or empty")
- Boundary 9: [0, 1] -> IllegalArgumentException("Input array cannot contain leading zeroes")
- Boundary 10: [-1] -> IllegalArgumentException("Input array cannot contain negative digits")
- Boundary 11: [10] -> IllegalArgumentException("Input array cannot contain digits greater than 9")
- Boundary 12: length of input array = 100, length of input array = 101 -> IllegalArgumentException("Input array cannot have more than 100 digits")

## 5. Devise test cases
Created test cases for each partition and boundary identified in the previous steps.

## 6. Automate the test cases
Created automated tests for each test case using JUnit.

## 7. Augment the test suite with creativity and experience
Added test case for not clearly written behavior for: Input array has 100 digits with only 9s and we add 1.

## Fixes
- Set pre-condition that a digits array can have at most 100 digits.
- Set pre-condition that the digits array does not contain leading zeroes.
- Set pre-condition that no digit inside the array is negative or greater 9.
- Extend array by one if we reach the part where all digits are 9.

# 2. Structural Tests
After executing the test suite again, we see that the constructor is not covered,
for that reason we added a private constructor to the class because it only has static methods.
This way we do not need to test the constructor, improve the source code and achieve 100% condition+line coverage.

# 3. Mutation Tests
The mutation coverage is 94%. 
The  mutant that survived was "changed conditional boundary" in line 25,
to also kill that mutant we implemented a new test case where an array with not leading zero is correctly computed.
After the implementation of this test case, the mutation coverage is 100% and all mutants are killed.