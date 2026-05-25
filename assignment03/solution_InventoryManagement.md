# Solution - Inventory Management

## Task A - Finding Low Stock Products

### 1. External Dependencies

The `InventoryManager` class depends on the following external component:

- `InventoryDatabaseConnector`

The `InventoryDatabaseConnector` itself communicates with the database layer (`InventoryDatabase`). This dependency should be replaced with a test double during unit testing because:

- Unit tests should focus only on the logic inside `InventoryManager`
- Real database connections make tests slow and unreliable
- Database availability should not affect unit test execution
- Using doubles allows us to simulate different scenarios easily

The `Product` class should **not** be mocked because:

- It is a simple value object
- It has no external side effects
- Using real objects makes tests more readable and realistic

### 2. Refactoring for Testability

The production code already uses constructor injection:

```java
public InventoryManager(InventoryDatabaseConnector databaseConnector)
```

This design already supports dependency injection and makes mocking possible without additional refactoring.

The tests were implemented using Mockito.

### Implemented Test Cases

#### `getLowStockProducts`

1. Returns only products with quantity less than 10
2. Returns an empty list when no low-stock products exist
3. Returns an empty list when inventory is empty
4. Closes the database connection even if an exception occurs

### 3. Disadvantages of Using Doubles

Although doubles are useful, they also have disadvantages:

#### a. Tests can become tightly coupled to implementation details

Example:

```java
verify(databaseConnector).close();
```

If the implementation changes internally, the test may fail even though the external behavior is still correct.

#### b. Mock behavior may differ from real systems

A mocked database connector may return perfectly formatted data, while the real database could return null values or malformed data.

#### c. Overuse of mocks reduces realism

If every dependency is mocked, tests may stop representing real production behavior.

---

# Task B - Filtering Products by Category

## TDD Iterative Process

### Step 1 - First Failing Test

Test written:

```java
shouldReturnProductsForGivenCategory()
```

Expected behavior:

- Return only products belonging to the requested category

Result:

- Test failed because `getProductsByCategory` was not implemented.

### Step 2 - Minimal Implementation

Implemented:

```java
return this.databaseConnector.getProductsByCategory(category);
```

The test passed.

### Step 3 - Edge Case Tests

Additional tests added:

1. Unknown category returns empty list
2. Null category returns empty list
3. Blank category returns empty list

### Step 4 - Refactoring

Added validation:

```java
if (category == null || category.trim().isEmpty()) {
    return Collections.emptyList();
}
```

Also ensured database connections are always closed using `finally`.

---

## Answers for Task B

### External Dependencies

Same as Task A:

- `InventoryDatabaseConnector` should be mocked
- `Product` objects should remain real

### Refactoring Needed?

No additional refactoring was needed because constructor injection already existed.

### Disadvantages of Doubles

Same disadvantages apply:

- Mock behavior may differ from real database behavior
- Tests may become implementation-dependent
- Excessive mocking can reduce confidence in integration correctness

