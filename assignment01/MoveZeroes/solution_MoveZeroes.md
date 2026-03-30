# 1. Specification-based Tests

## 1.1 Understand the Requirement, Inputs and Outputs
Read the problem description carefully to understand the requirements and expected inputs and outputs.

## 1.2 Explore the Program
There was a bug in the program in which the for loop continued only until "< numbers.length-1" which made the program drop the last non-zero element (or ignore).

By removing that -1 from the inequality for the loop, the program started working normally.

## 1.3 Identify the Partitions
We can have negative, zero and positive numbers.

What happens if we have zeroes spread out in the array?

What happens if the zeroes are consecutive?

What happens if there is no zero?

## 1.4 Analyze the Boundaries
The zeroes can be located anywhere in the array and our program can handle it pretty well.

Whether the zeroes are in the end, spread out or consecutive, it doesn't affect the functioning of the program.

This is tested by the test cases.

## 1.5 Devise Test Cases
Created test cases for each partition and boundary identified in the previous steps.

## 1.6 Automate Test Cases
Created automated tests for each test case using JUnit.

## 1.7 Augmentation of Test Suites
Added null check and see if it throws an IllegalArgumentException when there is no array.


# 2. Structural Tests
Line coverage is 92%. Only the first line, the class declaration is not covered according to Jacoco.

# 3. Mutation Tests
The PIT report shows:
- Line coverage: 92% (11/12)
- Mutation coverage: 100% (7/7)
- Test strength: 100%
