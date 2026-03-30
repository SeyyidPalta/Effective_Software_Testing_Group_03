package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BestTimeToBuyAndSellStockTest {
   
    @Test
    void testInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> BestTimeToBuyAndSellStock.maxProfit(null));
        assertThrows(IllegalArgumentException.class, () -> BestTimeToBuyAndSellStock.maxProfit(new int[]{}));
    }

    @ParameterizedTest
    @MethodSource("providePriceScenarios")
    void testMaxProfit(int[] prices, int expected) {
        assertEquals(expected, BestTimeToBuyAndSellStock.maxProfit(prices));
    }

    private static Stream<Arguments> providePriceScenarios() {
        return Stream.of(
            Arguments.of(new int[]{7, 1, 5, 3, 6, 4}, 5),  // Normal case
            Arguments.of(new int[]{7, 6, 4, 3, 1}, 0),     // Strictly decreasing
            Arguments.of(new int[]{1, 2, 3, 4, 5}, 4),     // Strictly increasing
            Arguments.of(new int[]{5, 5, 5, 5}, 0),        // Flat prices
            Arguments.of(new int[]{1, 10, 1, 10}, 9),      // Fluctuating
            Arguments.of(new int[]{10}, 0)                 // Single element boundary
        );
    }

    @Test
    void testNewMinimumPriceAfterProfitPotential() {
        int[] prices = {2, 1, 10};
        assertEquals(9, BestTimeToBuyAndSellStock.maxProfit(prices));
    }

    // Mutation tests
    @Test
    void testPriceEqualsMinPrice() {
        int[] prices = {2, 5, 2, 6}; 
        assertEquals(4, BestTimeToBuyAndSellStock.maxProfit(prices));
    }

    @Test
    void testProfitEqualsMaxProfit() {
        int[] prices = {1, 6, 1, 6};
        assertEquals(5, BestTimeToBuyAndSellStock.maxProfit(prices));
    }
}