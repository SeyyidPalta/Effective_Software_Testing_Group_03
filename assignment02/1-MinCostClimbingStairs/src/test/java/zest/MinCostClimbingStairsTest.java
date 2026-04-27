package zest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class MinCostClimbingStairsTest {

    MinCostClimbingStairs testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new MinCostClimbingStairs();
    }
    @AfterEach
    void tearDown() {
        testInstance = null;
    }

    @Test
    void testMinCostClimbingStairs() {
        int[] cost = {10, 15, 20};
        int expected = 15;
        int actual = testInstance.minCostClimbingStairs(cost);
        assertEquals(expected, actual);
    }

    @Test
    void testEmptyArray() {
        int[] cost = {};
        assertThrows(IllegalArgumentException.class, () -> testInstance.minCostClimbingStairs(cost));
    }

    @Test
    void testElementLowerThanMin() {
        int[] cost = {10, 15, -5};
        assertThrows(IllegalArgumentException.class, () -> testInstance.minCostClimbingStairs(cost));
    }

    @Test
    void testElementGreaterThanMax() {
        int[] cost = {10, 15, 1000};
        assertThrows(IllegalArgumentException.class, () -> testInstance.minCostClimbingStairs(cost));
    }

    @Test
    void testInvalidArgument() {
        assertThrows(IllegalArgumentException.class, () -> testInstance.minCostClimbingStairs(null));
    }
}
