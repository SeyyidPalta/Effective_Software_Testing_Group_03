package zest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseIntegerTest {
    /* explore */

    static Stream<Arguments> okValueProvider() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(1, 1),
                Arguments.of(-1, -1),
                Arguments.of(123, 321),
                Arguments.of(-123, -321),
                Arguments.of(120, 21),
                Arguments.of(-120, -21),
                Arguments.of(121, 121),
                Arguments.of(-121, -121),
                Arguments.of(2147483412, 2143847412),
                Arguments.of(-2147483412, -2143847412),
                Arguments.of(1010, 101),
                Arguments.of(-1010, -101),
                Arguments.of(-1000, -1)
        );
    }
    @ParameterizedTest
    @MethodSource("okValueProvider")
    void reverseInteger_ok(int value, int expected) {
        assertEquals(expected, ReverseInteger.reverse(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            Integer.MAX_VALUE + 1,
            Integer.MIN_VALUE - 1,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE
    })
    void reverseInteger_overflow_nok(int value) {
        assertEquals(0, ReverseInteger.reverse(value));
    }

}