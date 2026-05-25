# NewsAggregator Testing Solution

## Overview

This solution adds comprehensive JUnit 5 unit tests for the `NewsAggregator#getLatestNews` method using Mockito doubles for all external dependencies.

The tests focus on:

- Cache hit and cache miss scenarios
- API fallback behaviour
- Validation and boundary cases
- API downtime simulation
- Cache invalidation behaviour
- Empty response handling
- Maintainable and isolated test structure

## Testing Strategy

### External Dependencies Mocked

The following dependencies were replaced with Mockito mocks:

- `NewsAPI`
- `ContentCache`

This ensures:

- No real API calls are executed
- Cache behaviour can be controlled deterministically
- Tests remain isolated and fast

## Covered Test Scenarios

### 1. Cache Hit

Verifies that:

- Cached data is returned immediately
- API is never called
- Cache is not rewritten unnecessarily

### 2. Cache Miss

Verifies that:

- API is called when cache is empty/null
- Fresh results are cached
- Returned data matches API output

### 3. Empty Cache List

Boundary case ensuring:

- Empty cached collections are treated as cache misses
- Fresh data is fetched correctly

### 4. Invalid Categories

Validation tests for:

- `null`
- Empty string
- Blank string

Ensures proper exceptions are thrown and no dependency interactions occur.

### 5. API Downtime

Simulates external API failure using exceptions.

Verifies that:

- Exceptions propagate correctly
- Invalid cache writes do not occur

### 6. Cache Invalidation Scenario

Simulates invalidated cache by returning `null`.

Verifies that:

- Fresh data is fetched again
- Cache is repopulated

### 7. Empty API Response

Boundary case ensuring:

- Empty API responses are handled safely
- Empty collections are cached consistently

## Design & Maintainability Practices

The tests follow good unit testing practices:

- Arrange / Act / Assert structure
- Descriptive test names
- Isolation through mocks
- Reusable setup logic
- Focused assertions
- Behaviour verification with Mockito
- Boundary and negative case coverage

## Technologies Used

- JUnit 5
- Mockito
- Maven Surefire-compatible structure
