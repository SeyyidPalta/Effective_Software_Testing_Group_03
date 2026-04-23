# Solution: Convert Sorted List to Binary Search Tree

## Task 1: Code Coverage (100% Line Coverage)

- implemented test examples from the README.md
- implemented duplicate root test
- implemented duplicate leaf test
- implemented test for only negative values
- implemented test for larger list

Ignored test coverage for main method, focused on `sortedListToBST` method which contains the logic.

---

## Task 2: Designing Contracts

1. Not-null list:
   - `head == null` -> throw `IllegalArgumentException`

2. Sorted ascending list:
   - List elements must be in ascending order to the element before: `node[i].val <= node[i+1].val`

3. Node value range:
   - Each node value must satisfy: `-10^5 <= node[i].val <= 10^5`

4. List size Constraint:
   - List must contain: `0 <= list.size <= 2*10^4`

## Task 3: Contract Testing

Tests implemented in `ConvertSortedListToBinarySearchTreeConditionTest.java` to verify and ensure contract enforcement

---

## Task 4: Property-Based Testing (jqwik)

1. Null Input
2. Inorder Traversal equals input for 2 nodes
3. Valid binary search tree for n sorted nodes
4. Is height-balanced for n sorted nodes
5. Amount of input nodes equals amount of nodes in bst for n sorted nodes
6. All values in the input list are present in the output tree for n sorted nodes
7. Duplicate values still exists for n duplicate nodes
8. Single value is still present for a single value input