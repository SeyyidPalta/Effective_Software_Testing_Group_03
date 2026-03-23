# 1. Specification-based Tests

## 1. Understanding the requirements and outputs
Read the problem description carefully to understand the requirements and expected inputs / outputs

## 2. Explore what the program does for various inputs
- What happens if the input integer is single value: 0, 1, -1?
  - Result: 0, 1, -1
- What happens if the input integer is larger integer: 123, -123?
  - Result: 321, -321
- What happens if the input integer ends with zero: 120, -120?
  - Result: 21, -21
- What happens if the input integer is the maximum or minimum 32-bit integer: 2147483647, -2147483648?
  - Result: 0, 0
- What happens if the input integer exceeds the 32-bit integer range: 2147483648, -2147483649?
  - Result: 0, 0

## 3. Explore possible inputs and outputs, and identify partitions
- Input Partition 1: -2^31 ≤ x ≤ 2^31 - 1
- Input Partition 2: positive or negative integer exceeds the 32-bit integer range
- Output Partition 1: reversed integer is within the 32-bit integer range
- Output Partition 2: reversed integer exceeds the 32-bit integer range and returns 0
- Output Partition 3: reversed integer is the same as the input integer (single-digits and palindromes)
- Output Partition 4: reversed integer has leading zeroes (120 -> 21)

## 4. Analyze the boundaries
- Boundary 1: -2^31, -2^31 - 1
- Boundary 2: 2^31 + 1, 2^31

## 5. Devise test cases
Created test cases for each partition and boundary identified in the previous steps.

## 6. Automate the test cases
Created automated tests for each test case using JUnit.

## 7. Augment the test suite with creativity and experience
- Add palindrome test cases: 121 -> 121, -121 -> -121
- Add test case for max integer without overflow: 2147483412 -> 2143847412
- Add test case for min integer without overflow: -2147483412 -> -2143847412

# 2. Structural Tests
After executing the test suite again, we see that the constructor is not covered,
for that reason we added a private constructor to the class because it only has static methods.
This way we do not need to test the constructor, improve the source code.

Afterwards we see that this condition: `reversed > Integer.MAX_VALUE / 10 || (reversed == Integer.MAX_VALUE / 10 && digit > 7`
and also the condition for lower bound: `reversed < Integer.MIN_VALUE / 10 || (reversed == Integer.MIN_VALUE / 10 && digit < -8)`
is only partially covered and there is no number that fulfills the second part of the conditions,
(`(reversed == Integer.MAX_VALUE / 10 && digit > 7`) and (`(reversed == Integer.MIN_VALUE / 10 && digit < -8)`)
because the reverse of the input number is never equal to Integer.MAX_VALUE / 10 or Integer.MIN_VALUE / 10,
so we decide to delete this condition, because the int partition will throw an error anyway when we exceed the int condition.
With that we deleted unnecessary source code and we do not need to test those conditions anymore for a code coverage with 100%.

# 3. Mutation Tests

The result of the mutation tests are:	  	  
- line coverage: 92% (11/12)
- mutation coverage: 80% (8/10)
- test strength: 80% (8/10)

The two mutations that were not killed are in the conditional boundary that we deleted earlier, 
but we can not reach this code because the data type itself will throw an error when we exceed the int range, so we can not test those conditions and we can not kill those survivors.
