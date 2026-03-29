# 1. Specification-based Tests

## 1.1 Understand the Requirement, Inputs and Outputs
Read the problem description carefully to understand the requirements and expected inputs and outputs.

## 1.2 Explore the Program
Our program returns the longest common prefix among the list of strings. If there is no prefix, it returns "".
Tricky parts could be when strings have different length, or when they are identical. So, these behaviors were tested.

## 1.3 Identify the Partitions
We can have empty strings in a list, strings with one character each and strings with multiple characters (with same or different lengths).

## 1.4 Analyze the Boundaries
The program is case-sensitive. One of our test cases tries to send the list of 2 strings: "something" and "someTHING".

The program returns "some" as expected because the uppercase characters are encoded differently than their lowercase counterparts in ASCII.

## 1.5 Devise Test Cases
Created test cases for each partition and boundary identified in the previous steps.

## 1.6 Automate Test Cases
Created automated tests for each test case using JUnit.

## 1.7 Augmentation of Test Suites
Additional tests were added to make sure that the program doesn't work with a null input, and it throws an IllegalArgumentException.

# 2. Structural Tests
Line coverage in mutated class is 75%. Otherwise, the line coverage is 91% and branch coverage is 80%.

# 3. Mutation Tests
All mutants were killed by the test cases.
