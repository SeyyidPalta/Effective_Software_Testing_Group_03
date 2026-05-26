# OrderTracker Testing Solution

## Testing Strategy

### External Dependencies Mocked

The following dependencies were replaced with Mockito mocks:

- `DeliveryService`
- `OrderDashboardService`
- `AlertService`

## Covered Test Scenarios

### 1. Accuracy of Status Updates

Test implemented:

```java
updateOrderStatus_whenOrderIsOnTheWay_updatesDashboardWithoutAlert()
```

This verifies that:

- `DeliveryService#getLatestUpdate` provides the latest order data
- `OrderDashboardService#updateStatus` is called with the correct order ID and status
- No customer alert is sent for a normal non-delivered status update

### 2. Notification of Key Events

Test implemented:

```java
updateOrderStatus_whenOrderIsDelivered_updatesDashboardAndSendsDeliveryAlert()
```

This verifies that:

- Delivered orders still update the dashboard
- `AlertService#sendCustomerAlert` is called
- `ArgumentCaptor` captures both alert arguments
- The captured order ID and message content match the expected delivered-order notification

### 3. Response to Tracking Failures

Test implemented:

```java
updateOrderStatus_whenTrackingDataUnavailable_sendsTemporaryUnavailableAlert()
```

This simulates unavailable tracking data by making `DeliveryService#getLatestUpdate` return `null`.

The test verifies that:

- The dashboard is not updated with stale or missing data
- The customer receives the temporary tracking unavailable message
- `ArgumentCaptor` confirms the exact alert order ID and message

## Design & Maintainability Practices

The tests follow good unit testing practices:

- Arrange / Act / Assert structure
- Descriptive test method names
- Constructor injection used for testability
- Mockito mocks for external dependencies
- `ArgumentCaptor` used where message content matters
- Focused assertions for one behavior per test
- No real external service calls