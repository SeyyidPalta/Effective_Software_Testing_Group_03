# 1. Specification-based Tests

## 1.1 Understand the Requirement, Inputs and Outputs
Read the problem description carefully to understand the requirements and expected inputs and outputs.

## 1.2 Explore the Program
What happens if the string is empty?
- It returns 0 because there is no last word.

What happens if there are some spaces after the last word?
- Spaces are ignored and the length of the last word is returned.

What happens if there are only spaces before the last word?
- Spaces are ignored yet again and the length of the last word is returned.

## 1.3 Identify the Partitions
We can either have an empty string. A string with one word and a string with multiple words.

## 1.4 Analyze the Boundaries
The program is case-insensitive as the case doesn't play a role, and it counts the number of letters in a word.

## 1.5 Devise Test Cases
Created test cases for each partition and boundary identified in the previous steps.

## 1.6 Automate Test Cases
Created automated tests for each test case using JUnit.

## 1.7 Augmentation of Test Suites
Added tests for tricky parts such as spaces after the last word and a punctuation. 
The program counts punctuations as part of the last word.


# 2. Structural Tests
Line coverage is 91%. The only line that is not covered by tests is the class header line (3)

# 3. Mutation Tests
One mutant could not be killed because the condition where i equals to 0 and also character at that location being " " could not be satisfied.