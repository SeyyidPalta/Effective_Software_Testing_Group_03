package zest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
