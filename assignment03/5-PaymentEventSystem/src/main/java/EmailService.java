import java.util.ArrayList;
import java.util.List;

public class EmailService implements PaymentListener {
    private String lastSentMessage;

    private final List<PaymentEvent> receivedEvents = new ArrayList<>();

    @Override
    public void onPaymentSuccess(PaymentEvent event) {
        receivedEvents.add(event);
        lastSentMessage = "Payment confirmation sent to " + event.getCustomerEmail()
                + " for payment ID: " + event.getPaymentId()
                + " Amount: " + event.getAmount();
        System.out.println(lastSentMessage);
        // Assume here you would send a real email.
    }

    public String getLastSentMessage() {
        return lastSentMessage;
    }

    public List<PaymentEvent> getReceivedEvents() {
        return receivedEvents;
    }
}
