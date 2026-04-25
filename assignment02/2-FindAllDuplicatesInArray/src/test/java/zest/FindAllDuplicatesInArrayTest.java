package zest;

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
    public void testEmptyFindDuplicates() {
        int[] testList = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> expected = List.of();
        assertEquals(expected, testInstance.findDuplicates(testList));
    }

    @Test
    public void testFindDuplicates() {
        int[] testList = {1, 2, 2, 3, 4, 5, 6, 7, 8, 9, 9};
        List<Integer> expected = List.of(2, 9);
        assertEquals(expected, testInstance.findDuplicates(testList));
    }
}
