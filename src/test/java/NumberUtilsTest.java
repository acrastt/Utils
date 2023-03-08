import static org.example.acrastt.utils.NumberUtils.convertToOrdinal;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NumberUtilsTest {

  @Test
  void test() {
    // Test the convertToOrdinal method
    assertEquals("1st", convertToOrdinal(1));
    assertEquals("2nd", convertToOrdinal(2));
    assertEquals("3rd", convertToOrdinal(3));
    assertEquals("4th", convertToOrdinal(4));
    assertEquals("5th", convertToOrdinal(5));
  }
}
