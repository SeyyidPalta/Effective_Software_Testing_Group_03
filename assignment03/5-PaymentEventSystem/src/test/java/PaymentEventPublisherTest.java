import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentEventPublisherTest {
    private int paymentId = 100;
    private final Supplier<String> paymentIdGenerator = () -> String.valueOf(++paymentId);
    private final Supplier<String> mailAddressGenerator = () -> String.format("customer-%s@mail.ch", ++paymentId);
    private final Supplier<Double> amountGenerator = () -> 100.0 + paymentId;

    private final Supplier<PaymentEvent> paymentEventGenerator = () -> new PaymentEvent(
            paymentIdGenerator.get(),
            mailAddressGenerator.get(),
            amountGenerator.get()
    );

    @Spy
    private EmailService email = new EmailService();
    @Spy
    private PaymentListener email2 = new EmailService();
    @Spy
    private PaymentListener invoice = new InvoiceService();

    @Captor
    private ArgumentCaptor<PaymentEvent> eventCaptor;

    @Test
    void testSubscribe_noListeners() {
        PaymentEventPublisher publisher = new PaymentEventPublisher();
        PaymentEvent event1 = paymentEventGenerator.get();
        publisher.publishPaymentSuccess(event1);

        Mockito.verify(email, never()).onPaymentSuccess(eventCaptor.capture());
        Mockito.verify(invoice, never()).onPaymentSuccess(eventCaptor.capture());
        assertTrue(eventCaptor.getAllValues().isEmpty());
    }

    @Test
    void testSubscribe_emailListener() {
        PaymentEventPublisher publisher = new PaymentEventPublisher();
        PaymentEvent event1 = paymentEventGenerator.get();

        publisher.subscribe(email);
        publisher.publishPaymentSuccess(event1);

        Mockito.verify(email, times(1)).onPaymentSuccess(eventCaptor.capture());
        Mockito.verify(invoice, never()).onPaymentSuccess(eventCaptor.capture());
        assertPaymentEventEquals(eventCaptor.getValue(), event1);
    }

    @Test
    void testSubscribe_subscribeTwoEmailListener() {
        PaymentEventPublisher publisher = new PaymentEventPublisher();
        PaymentEvent event1 = paymentEventGenerator.get();

        publisher.subscribe(email);
        publisher.subscribe(email2);
        publisher.publishPaymentSuccess(event1);

        Mockito.verify(email, times(1)).onPaymentSuccess(eventCaptor.capture());
        Mockito.verify(email2, times(1)).onPaymentSuccess(eventCaptor.capture());
        Mockito.verify(invoice, never()).onPaymentSuccess(eventCaptor.capture());
        assertPaymentEventEquals(eventCaptor.getValue(), event1);
    }

    @Test
    void testSubscribe_subscribeListenersTwoEvents() {
        PaymentEventPublisher publisher = new PaymentEventPublisher();
        PaymentEvent event1 = paymentEventGenerator.get();
        PaymentEvent event2 = paymentEventGenerator.get();

        publisher.subscribe(email);
        publisher.subscribe(invoice);
        publisher.publishPaymentSuccess(event1);
        publisher.publishPaymentSuccess(event2);

        Mockito.verify(email, times(2)).onPaymentSuccess(eventCaptor.capture());
        Mockito.verify(invoice, times(2)).onPaymentSuccess(eventCaptor.capture());
        assertPaymentEventEquals(eventCaptor.getAllValues().get(0), event1);
        assertPaymentEventEquals(eventCaptor.getAllValues().get(1), event2);
    }

    @Test
    void testSubscribe_subscribeSameListenerMultipleTimes() {
        PaymentEventPublisher publisher = new PaymentEventPublisher();
        PaymentEvent event1 = paymentEventGenerator.get();
        int x = 20;
        for(int i = 0; i < x; i++) {
            publisher.subscribe(email);
            publisher.subscribe(invoice);
        }
        publisher.publishPaymentSuccess(event1);

        Mockito.verify(email, times(x)).onPaymentSuccess(eventCaptor.capture());
        Mockito.verify(invoice, times(x)).onPaymentSuccess(eventCaptor.capture());
        for (int i = 0; i < x; i++) {
            assertPaymentEventEquals(eventCaptor.getAllValues().get(i), event1);
        }
    }

    @Test
    void testSubscribe_invoiceMultipleEvents() {
        PaymentEventPublisher publisher = new PaymentEventPublisher();
        publisher.subscribe(invoice);

        List<PaymentEvent> actualEvents = new ArrayList<>();

        int x = 20;
        for(int i = 0; i < x; i++) {
            PaymentEvent event = paymentEventGenerator.get();
            actualEvents.add(event);
            publisher.publishPaymentSuccess(event);
        }

        Mockito.verify(email, never()).onPaymentSuccess(eventCaptor.capture());
        Mockito.verify(invoice, times(x)).onPaymentSuccess(eventCaptor.capture());
        for (int i = 0; i < x; i++) {
            assertPaymentEventEquals(eventCaptor.getAllValues().get(i), actualEvents.get(i));
        }
    }

    @Test
    void testSubscribe_emailMultipleEvents_woCaptor() {
        PaymentEventPublisher publisher = new PaymentEventPublisher();
        publisher.subscribe(email);

        List<PaymentEvent> actualEvents = new ArrayList<>();

        int x = 20;
        for(int i = 0; i < x; i++) {
            PaymentEvent event = paymentEventGenerator.get();
            actualEvents.add(event);
            publisher.publishPaymentSuccess(event);
        }

        Mockito.verify(email, times(x)).onPaymentSuccess(any());
        Mockito.verify(invoice, never()).onPaymentSuccess(any());
        for (int i = 0; i < x; i++) {
            assertPaymentEventEquals(email.getReceivedEvents().get(i), actualEvents.get(i));
        }
    }

    private static void assertPaymentEventEquals(PaymentEvent expected, PaymentEvent actual) {
        assertEquals(expected.getPaymentId(), actual.getPaymentId());
        assertEquals(expected.getCustomerEmail(), actual.getCustomerEmail());
        assertEquals(expected.getAmount(), actual.getAmount());
    }
}
