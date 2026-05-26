import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderTrackerTest {
    private static final String ORDER_ID = "order-123";

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private OrderDashboardService dashboardService;

    @Mock
    private AlertService alertService;

    private OrderTracker orderTracker;

    @BeforeEach
    void setUp() {
        orderTracker = new OrderTracker(deliveryService, dashboardService, alertService);
    }

    @Test
    void updateOrderStatus_whenOrderIsOnTheWay_updatesDashboardWithoutAlert() {
        OrderUpdate update = new OrderUpdate(ORDER_ID, OrderStatus.ON_THE_WAY, "Courier is nearby");
        when(deliveryService.getLatestUpdate(ORDER_ID)).thenReturn(update);

        orderTracker.updateOrderStatus(ORDER_ID);

        verify(dashboardService).updateStatus(ORDER_ID, OrderStatus.ON_THE_WAY);
        verifyNoInteractions(alertService);
    }

    @Test
    void updateOrderStatus_whenOrderIsDelivered_updatesDashboardAndSendsDeliveryAlert() {
        OrderUpdate update = new OrderUpdate(ORDER_ID, OrderStatus.DELIVERED, "Front door");
        when(deliveryService.getLatestUpdate(ORDER_ID)).thenReturn(update);
        ArgumentCaptor<String> orderIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        orderTracker.updateOrderStatus(ORDER_ID);

        verify(dashboardService).updateStatus(ORDER_ID, OrderStatus.DELIVERED);
        verify(alertService).sendCustomerAlert(orderIdCaptor.capture(), messageCaptor.capture());
        assertEquals(ORDER_ID, orderIdCaptor.getValue());
        assertEquals("Your order has been delivered: Front door", messageCaptor.getValue());
    }

    @Test
    void updateOrderStatus_whenTrackingDataUnavailable_sendsTemporaryUnavailableAlert() {
        when(deliveryService.getLatestUpdate(ORDER_ID)).thenReturn(null);
        ArgumentCaptor<String> orderIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        orderTracker.updateOrderStatus(ORDER_ID);

        verifyNoInteractions(dashboardService);
        verify(alertService).sendCustomerAlert(orderIdCaptor.capture(), messageCaptor.capture());
        assertEquals(ORDER_ID, orderIdCaptor.getValue());
        assertEquals("Order tracking is temporarily unavailable. Please check again later.",
                messageCaptor.getValue());
    }
}
