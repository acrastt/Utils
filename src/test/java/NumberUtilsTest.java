import org.example.acrastt.utils.NumberUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberUtilsTest {
    @Test
    void test() {
        assertEquals("1st", NumberUtils.convertToOrdinal(1));
        assertEquals("2nd", NumberUtils.convertToOrdinal(2));
        assertEquals("3rd", NumberUtils.convertToOrdinal(3));
        assertEquals("4th", NumberUtils.convertToOrdinal(4));
        assertEquals("5th", NumberUtils.convertToOrdinal(5));
        assertEquals("6th", NumberUtils.convertToOrdinal(6));
        assertEquals("7th", NumberUtils.convertToOrdinal(7));
        assertEquals("8th", NumberUtils.convertToOrdinal(8));
        assertEquals("9th", NumberUtils.convertToOrdinal(9));
    }
}
