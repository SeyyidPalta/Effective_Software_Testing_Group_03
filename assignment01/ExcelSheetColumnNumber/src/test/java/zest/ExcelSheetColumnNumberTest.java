package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelSheetColumnNumberTest {


    // Error Handling & Partitions (Invalid Inputs)

    @Test
    void testNullOrEmptyInputs() {
        assertThrows(IllegalArgumentException.class, () -> ExcelSheetColumnNumber.titleToNumber(null));
        assertThrows(IllegalArgumentException.class, () -> ExcelSheetColumnNumber.titleToNumber(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "@",    // ASCII 64 (below 'A' which is 65)
        "[",    // ASCII 91 (above 'Z' which is 90)
        "a",    // Lowercase
        "1",    // Number
        "A1",   // Mixed valid and invalid inputs
        " A"    // Leading space
    })
    void testInvalidCharacters(String invalidInput) {
        assertThrows(IllegalArgumentException.class, () -> ExcelSheetColumnNumber.titleToNumber(invalidInput));
    }

    // Core Logic & Boundaries (Valid Inputs)

    @ParameterizedTest
    @CsvSource({
        "A, 1",         // Boundary: Lowest single character
        "Z, 26",        // Boundary: Highest single character
        "AA, 27",       // Boundary: Lowest double character
        "AZ, 52",       // Internal double character
        "BA, 53",       // Roll-over of first digit
        "ZY, 701",      // Internal double character
        "ZZ, 702",      // Boundary: Highest double character
        "AAA, 703",     // Boundary: Lowest triple character
        "FXSHRXW, 2147483647" // Boundary: Integer.MAX_VALUE equivalent
    })
    void testValidColumnTitles(String columnTitle, int expected) {
        assertEquals(expected, ExcelSheetColumnNumber.titleToNumber(columnTitle));
    }
}