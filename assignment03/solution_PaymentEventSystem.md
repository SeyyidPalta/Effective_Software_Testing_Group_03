# Payment Event System

## A. Number of invocations

We create spies of mail and invoice services to verify that the `onPaymentSuccess` method is called the expected number of times. 
We subscribe both services to the publisher and then call `publishPaymentSuccess`. 
Finally, we verify that both services received the payment event.

### Tests that were implemented:
- `testSubscribe_noListeners`: No service subscribed, so no events should occur and email and invoice service should never be called.
- `testSubscribe_emailListener`: Only email service subscribed, so the event should be published once and email service should be called once, but never the invoice service.
- `testSubscribe_subscribeTwoEmailListener`: Two different email services are subscribed, so the event should be published twice and each email service should be called once, but never the invoice service.
- `testSubscribe_subscribeListenersTwoEvents`: Both email and invoice services are subscribed, so the event should be published twice for each event of the two events and both services should be called twice.
- `testSubscribe_subscribeSameListenerMultipleTimes`: Both email and invoice services are subscribed multiple times, so the event should be published as many times as the number of subscriptions and both services should be called as many times as they are subscribed.
- `testSubscribe_invoiceMultipleEvents`: Only invoice service subscribed, but with multiple events, so the service should also be called as many times as the events occur.

## B. Content of invocations—`ArgumentCaptor`

We use `ArgumentCaptor` to capture the payment event details passed to the services and verify that they are correct.
We create an `ArgumentCaptor` for the `PaymentEvent` class and use it to capture the arguments passed to the `onPaymentSuccess` method of both services. 
Then, we assert that the captured payment event details (payment ID, customer email, amount) are correct with the new method `assertPaymentEventDetails`.
We extended the previous tests to include assertions for the content of the payment event from the argument captor.

## C. Content of invocations—Increasing observability
To increase the observability of the `EmailService` class, we added a method `getReceivedEvents` that returns a list of received payment events.
The list is a new variable inside the class that is updated every time the `onPaymentSuccess` method is called (`add(event)`).
This allows us to get the list in the tests and verify the details of the payment events received by the `EmailService` without using `ArgumentCaptor`.
As example we implemented the last test: `testSubscribe_invoiceMultipleEvents` again for the `InvoiceService`, 
but for the `EmailService` but without captor: `testSubscribe_emailMultipleEvents_woCaptor`.

## D. Advantages
The `ArgumentCaptor` technique allows us to capture and verify the arguments passed to the method, 
which is useful when we want to check the details of the payment event without modifying the production code.   
But it can make the tests more complex and less readable, especially when there are multiple arguments or when the arguments are complex objects.
On the other hand, increasing observability like adding a simple getter that keep track of the received events allows us to verify the details of the payment events without needing to capture the arguments, 
which can make the tests cleaner and more straightforward. 
However, it also means that we are modifying the production code to add test-specific functionality. 
It is a trade-off between test maintainability and code cleanliness, as adding test-specific code to the production code and may causes vulnerabilities when it is not implement correctly.