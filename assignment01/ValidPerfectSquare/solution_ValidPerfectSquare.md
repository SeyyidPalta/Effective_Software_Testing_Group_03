# 1. Specification-based Tests

## 1. Understanding the requirements and outputs
Read the problem description carefully to understand the requirements and expected inputs / outputs

## 2. Explore what the program does for various inputs
- What happens for a perfect square: 1, 4, 9, 16, 100, 9_999, 10_000, 999_999, 1_000_000?
  - Result: true
- What happens for a perfect square: 100_000_000?
  - Result: false
  - Expected: true
  - Fix: Changed while condition: `while (left <= right)` to prevent that the loop terminates before checking the last possible value of `mid`
- What happens for a non-perfect square: 2, 3, 5, 1_000_001, 300_000_000, Integer.MAX_VALUE?
  - Result: false
- What happens for numbers out of the defined boundary: 0, -1, Integer.MAX_VALUE + 1?
  - Result: IllegalArgumentException

## 3. Explore possible inputs and outputs, and identify partitions
- Input Partition 1: 1 ≤ num ≤ 2^31 - 1
- Input Partition 2: num is a perfect square
- Input Partition 3: num is not a perfect square
- Input Partition 4: num is out of the defined boundary (num ≤ 0 or num > Integer.MAX_VALUE)
- Output Partition 1: true if num is a perfect square
- Output Partition 2: false if num is not a perfect square
- Output Partition 3: IllegalArgumentException if num is out of the defined boundary

## 4. Analyze the boundaries
- Boundary 1: num = 0, num = 1
- Boundary 2: num = 2^31 - 1, num = 2^31

## 5. Devise test cases
Created test cases for each partition and boundary identified in the previous steps.

## 6. Automate the test cases
Created automated tests for each test case using JUnit.

## 7. Augment the test suite with creativity and experience
- Highest perfect square within the integer range: 46_340^2 = 2_147_395_600

# 2. Structural Tests
After changing the condition the test are running successfully, 
with a test coverage from 95%. Only the line for the constructor is missing again, 
so we added a empty constructor again to the class because it only has one static method.
This way we do not need to test the constructor, 
improve the source code and achieve 100% condition+line coverage.

# 3. Mutation Tests

The PIT report shows:
- Line coverage: 94% (16/17)
- Mutation coverage: 94% (16/17)
- Test strength: 94% (16/17)

One survivor was not killed: changed conditional boundary for `else if (square < num)`, 
but we can not a test to kill that mutant because in this scenario `<` and `<=` will have the same result, so we can not distinguish between them with a test case.

There were also multiple timeouts: 
- `long mid = left + (right - left) / 2;`: Replaced long subtraction with addition
  - In this case mid will always increase and never fulfill one of the conditions and always run the else statement so the loop will never terminate and the test will timeout
- `left = mid + 1;`: Replaced long addition with subtraction
- `right = mid - 1;`: Replaced long subtraction with addition