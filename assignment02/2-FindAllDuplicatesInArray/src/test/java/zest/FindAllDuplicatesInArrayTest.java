package zest;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Provide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindAllDuplicatesInArrayTest {

    protected FindAllDuplicatesInArray testInstance;

    @BeforeEach
    public void setUp() {
        try {
            testInstance = new FindAllDuplicatesInArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @AfterEach
    public void tearDown() {
        try {
            testInstance = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEmptyFindDuplicates() {
        int[] testList = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> expected = List.of();
        assertEquals(expected, testInstance.findDuplicates(testList));
    }

    @Test
    void testFindDuplicates() {
        int[] testList = {1, 2, 2, 3, 4, 5, 6, 7, 8, 9, 9};
        List<Integer> expected = List.of(2, 9);
        assertEquals(expected, testInstance.findDuplicates(testList));
    }

    @Test
    void testElementOccurringMoreThanTwice() {
        int[] testList = {1, 2, 2, 2, 2};
        assertThrows(IllegalArgumentException.class, () -> testInstance.findDuplicates(testList));
    }

    @Test
    void testEmptyArray() {
        int[] testList = {};
        assertThrows(IllegalArgumentException.class, () -> testInstance.findDuplicates(testList));
    }

    @Provide
    Arbitrary<int[]> validArrays() {
        return Arbitraries.integers().between(1, Integer.MAX_VALUE)
                .array(int[].class)
                .ofMinSize(1)
                .ofMaxSize((int) Math.pow(10, 5));
    }
}
