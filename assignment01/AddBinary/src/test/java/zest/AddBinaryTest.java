package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class AddBinaryTest {

    // Null & Error handling
    @Test
    void testNullInputs() {
        assertThrows(IllegalArgumentException.class, () -> AddBinary.addBinary(null, "1"));
        assertThrows(IllegalArgumentException.class, () -> AddBinary.addBinary("1", null));
    }

    // Identity Partition
    @ParameterizedTest
    @CsvSource({
        "0, 0, 0",
        "0, 1, 1",
        "1, 0, 1",
        "0, 1011, 1011",
        "1101, 0, 1101"
    })
    void testZeroIdentity(String a, String b, String expected) {
        assertEquals(expected, AddBinary.addBinary(a, b));
    }

    // Length variation partitions
    @ParameterizedTest
    @CsvSource({
        "1, 10, 11",           // b is longer -> no carry
        "10, 1, 11",           // a is longer -> no carry
        "1, 11, 100",          // b is longer -> carry
        "11, 1, 100",          // a is longer -> carry
        "101010, 1, 101011",   // large length difference
        "1, 101010, 101011"
    })
    void testLengthAsymmetry(String a, String b, String expected) {
        assertEquals(expected, AddBinary.addBinary(a, b));
    }

    // Carry Propagation boundaries
    @ParameterizedTest
    @CsvSource({
        "11, 11, 110",         // Internal carry and final carry
        "111, 111, 1110",      // All bits generate carry
        "101, 011, 1000",      // Carry ripples through multiple zeros
        "1111, 1, 10000",      // Boundary: Max ripple effect
        "1111, 1111, 11110"    // Equal length maximum carries
    })
    void testCarryPropagation(String a, String b, String expected) {
        assertEquals(expected, AddBinary.addBinary(a, b));
    }

    // Large scale test
    @Test
    void testLargeStrings() {
        // String of 10,000 '1's + '1' should result in '1' followed by 10,000 '0's
        String a = "1".repeat(10000);
        String b = "1";
        String expectedStart = "100000";
        
        String result = AddBinary.addBinary(a, b);
        
        assertEquals(10001, result.length());
        assertTrue(result.startsWith("100"));
        assertTrue(result.endsWith("000"));
    }
}