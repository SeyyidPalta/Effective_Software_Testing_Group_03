import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryManagerTest {

    private InventoryDatabaseConnector databaseConnector;
    private InventoryManager inventoryManager;

    @BeforeEach
    void setUp() {
        databaseConnector = mock(InventoryDatabaseConnector.class);
        inventoryManager = new InventoryManager(databaseConnector);
    }

    @Test
    void shouldReturnOnlyProductsWithQuantityLessThanTen() {
        List<Product> products = Arrays.asList(
                new Product("1", "Laptop", "Electronics", 5, 1500.0),
                new Product("2", "Mouse", "Electronics", 10, 25.0),
                new Product("3", "Keyboard", "Electronics", 2, 70.0)
        );

        when(databaseConnector.getAllProducts()).thenReturn(products);

        List<Product> result = inventoryManager.getLowStockProducts();

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(product -> product.getQuantity() < 10));
        verify(databaseConnector).close();
    }

    @Test
    void shouldReturnEmptyListWhenNoLowStockProductsExist() {
        List<Product> products = Arrays.asList(
                new Product("1", "Laptop", "Electronics", 10, 1500.0),
                new Product("2", "Monitor", "Electronics", 20, 300.0)
        );

        when(databaseConnector.getAllProducts()).thenReturn(products);

        List<Product> result = inventoryManager.getLowStockProducts();

        assertTrue(result.isEmpty());
        verify(databaseConnector).close();
    }

    @Test
    void shouldReturnEmptyListWhenInventoryIsEmpty() {
        when(databaseConnector.getAllProducts()).thenReturn(Collections.emptyList());

        List<Product> result = inventoryManager.getLowStockProducts();

        assertTrue(result.isEmpty());
        verify(databaseConnector).close();
    }

    @Test
    void shouldCloseConnectionEvenWhenExceptionOccurs() {
        when(databaseConnector.getAllProducts())
                .thenThrow(new RuntimeException("Database failure"));

        assertThrows(RuntimeException.class,
                () -> inventoryManager.getLowStockProducts());

        verify(databaseConnector).close();
    }

    @Test
    void shouldReturnProductsForGivenCategory() {
        List<Product> electronicsProducts = Arrays.asList(
                new Product("1", "Laptop", "Electronics", 5, 1500.0),
                new Product("2", "Mouse", "Electronics", 25, 25.0)
        );

        when(databaseConnector.getProductsByCategory("Electronics"))
                .thenReturn(electronicsProducts);

        List<Product> result = inventoryManager.getProductsByCategory("Electronics");

        assertEquals(2, result.size());
        assertTrue(result.stream()
                .allMatch(product -> product.getCategory().equals("Electronics")));
        verify(databaseConnector).getProductsByCategory("Electronics");
        verify(databaseConnector).close();
    }

    @Test
    void shouldReturnEmptyListForUnknownCategory() {
        when(databaseConnector.getProductsByCategory("Toys"))
                .thenReturn(Collections.emptyList());

        List<Product> result = inventoryManager.getProductsByCategory("Toys");

        assertTrue(result.isEmpty());
        verify(databaseConnector).close();
    }

    @Test
    void shouldReturnEmptyListForNullCategory() {
        List<Product> result = inventoryManager.getProductsByCategory(null);

        assertTrue(result.isEmpty());
        verify(databaseConnector, never()).getProductsByCategory(any());
        verify(databaseConnector).close();
    }

    @Test
    void shouldReturnEmptyListForBlankCategory() {
        List<Product> result = inventoryManager.getProductsByCategory("   ");

        assertTrue(result.isEmpty());
        verify(databaseConnector, never()).getProductsByCategory(any());
        verify(databaseConnector).close();
    }
}
