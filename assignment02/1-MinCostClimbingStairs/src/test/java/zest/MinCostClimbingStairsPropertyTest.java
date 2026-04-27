package zest;

import net.jqwik.api.Assume;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.PropertyDefaults;

import static org.junit.jupiter.api.Assertions.assertThrows;

@PropertyDefaults(tries = 100)
public class MinCostClimbingStairsPropertyTest {

    private final MinCostClimbingStairs testInstance = new MinCostClimbingStairs();
    private final int MAX_LEN = 1000;
    private final int MIN_LEN = 2;

    @Property
    void emptyArray() {
        assertThrows(IllegalArgumentException.class, () -> testInstance.minCostClimbingStairs(null));
    }

    @Property
    void arrayLengthRangeMin(
            @ForAll int[] costArray
    ) {
        Assume.that(costArray.length < MIN_LEN);
        assertThrows(IllegalArgumentException.class, () -> testInstance.minCostClimbingStairs(costArray));
    }

}
