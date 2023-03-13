import org.example.acrastt.utils.NumberUtils;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberUtilsTest {

    @RepeatedTest(10)
    void test() {
        // Test the convertToOrdinal method
        for (int i = 0; i < 100; i++) {
            assertEquals(i + (i == 11 || i == 12 || i == 13 ? "th" :
                            i % 10 == 1 ? "st" : i % 10 == 2 ? "nd" : i % 10 == 3 ? "rd" : "th"),
                    NumberUtils.convertToOrdinal(i));
        }
    }
}
