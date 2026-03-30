# 1. Specification-based Tests

## 1.1 Understand the Requirement, Inputs and Outputs
Read the problem description carefully to understand the requirements and expected inputs and outputs.

## 1.2 Explore the Program
What happens if we have an empty string for both haystack and needle?
- Result: 0 because of trivial solution.

What happens if the input is type of null or just invalid?
- Result: IllegalArgumentException("Input string cannot be null")

What happens if the needle is longer than the haystack and still containing some substring?
- Result: -1

What happens if the cases are different (upper and lower)?
- Result: -1
- Even for same substrings, the case is sensitive.

## 1.3 Identify the Partitions
- Case Sensitiveness
- Special Characters
- Illegal Arguments such as null
- Numbers

## 1.4 Analyze the Boundaries
The program is case-sensitive, and doesn't recognize the same substrings if they have different cases.
Special characters don't cause any issue and can be recognized as substring (needle).
Illegal arguments such as null or not string raise IllegalArgumentsException.
Numbers can also part of strings, and they don't cause any issue either and can be recognized as substring similar to special characters.

## 1.5 Devise Test Cases
Created test cases for each partition and boundary identified in the previous steps.

## 1.6 Automate Test Cases
Created automated tests for each test case using JUnit.

## 1.7 Augmentation of Test Suites
Added test cases for tricky parts such as partial match when it comes to a needle that partly exists in haystack.

# 2. Structural Tests
Line coverage is 94%. The only line that is not covered is where the class name is defined (line 3).
Otherwise, the current test cases cover the branches and different conditions.

# 3. Mutation Tests
- Line coverage: 93% (13/14)
- Mutation coverage: 100% (15/15)
- Test strength: 100% (15/15)