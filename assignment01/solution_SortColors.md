# 1. Specification-based Tests

## 1. Understanding the requirements and outputs
Read the problem description carefully to understand the requirements and expected inputs / outputs

Missing behaviour in edge cases:
- What happens for an empty array?
- What happens if the array gets other values than 0, 1, and 2?
- What happens if nums.length is greater than 300?

## 2. Explore what the program does for various inputs
- What happens for an unordered array: [2, 0, 2, 1, 1, 0]?
  - Result: [0, 0, 1, 1, 2, 2]
- What happens for an array with only one entry: [0], [2]?
  - Result: [0], [2]
- What happens for an array with only one color: [0, 0, 0]?
  - Result: [0, 0, 0]
- What happens for an already sorted array: [0, 1, 2]?
  - Result: [0, 1, 2]
- What happens for alternating color patterns: [0, 2, 0, 2, 0]?
  - Result: [0, 2, 0, 2, 0] -> [0, 0, 0, 2, 2]
- What happens for an empty array: []?
  - Result: []
  - Expected: IllegalArgumentException
  - Fix: Set pre-condition that the input array cannot be null or empty
- What happens for an null array: null?
  - Result: IllegalArgumentException
- What happens for an array with values other than 0, 1, and 2: [3], [-1], [3, 0, 1], [-1, 0, 1], [1, 3]?
  - Result: [3] -> [3], [-1] -> [-1], [3, 0, 1] -> [0, 1, 3], [-1, 0, 1] -> [0, 1, -1], [1, 3] -> [1, 3]
  - Expected: IllegalArgumentException
  - Fix: Set pre-condition that the input array only contains 0, 1, and 2
- What happens for an array with more than 300 entries? [1, 0, ..., 0] (301 entries)?
  - Result: [1, 0, ..., 0] -> [0, ,..., 1]
  - Expected: IllegalArgumentException
  - Fix: Set pre-condition that the input array has at most 300 entries

## 3. Explore possible inputs and outputs, and identify partitions
- Input Partition 1: 1 ≤ nums.length ≤ 300
- Input Partition 2: nums[i] is either 0, 1, or 2
- Output Partition 1: array is sorted in the order 0s, then 1s, then 2s
- Output Partition 2: array throws IllegalArgumentException if the input array is null
- Output Partition 3: array throws IllegalArgumentException if the input array contains values other than 0, 1, and 2
- Output Partition 4: array throws IllegalArgumentException if the input array is empty or has more than 300 entries

## 4. Analyze the boundaries
- Boundary 1: [-1], [0]
- Boundary 2: [2], [3]
- Boundary 3: num.length = 0, num.length = 1, 
- Boundary 4: num.length = 300, num.length = 301
- Boundary 5: null

## 5. Devise test cases
Created test cases for each partition and boundary identified in the previous steps.

## 6. Automate the test cases
Created automated tests for each test case using JUnit.

## 7. Augment the test suite with creativity and experience

## Fixes
- Extend the requirements with the behaviour for edge cases such as an empty array and values outside 0, 1 and 2
- Set pre-condition that the input array only contains 0, 1, and 2
- Set pre-condition that the input array cannot be empty
- Set pre-condition that the input array has at most 300 entries

# 2. Structural Tests
After the implementing the fixes and extending the requirements, we run the test suite again and look in the test coverage report from JaCoCo.
The test coverage is 96%, only the line for the constructor is missing again, so we added a empty constructor again to the class because it only has static methods. 
This way we do not need to test the constructor, improve the source code and achieve 100% condition+line coverage.

# 3. Mutation Tests

The PIT report shows:
- Line coverage: 96% (25/26)	
- Mutation coverage: 100% (16/19)	
- Test strength: 100% (16/19)

The private constructor is not covered, but we do not need to test it because it is a private empty constructor 
and the class only has static methods.

