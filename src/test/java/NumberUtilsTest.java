import org.example.acrastt.utils.NumberUtils;
import org.junit.jupiter.api.Test;

class NumberUtilsTest {
    @Test
    void test() {
        assert NumberUtils.convertToOrdinal(1).equals("1st");
        assert NumberUtils.convertToOrdinal(2).equals("2nd");
        assert NumberUtils.convertToOrdinal(3).equals("3rd");
        assert NumberUtils.convertToOrdinal(4).equals("4th");
        assert NumberUtils.convertToOrdinal(5).equals("5th");
        assert NumberUtils.convertToOrdinal(6).equals("6th");
        assert NumberUtils.convertToOrdinal(7).equals("7th");
        assert NumberUtils.convertToOrdinal(8).equals("8th");
        assert NumberUtils.convertToOrdinal(9).equals("9th");
        assert NumberUtils.convertToOrdinal(10).equals("10th");
        assert NumberUtils.convertToOrdinal(11).equals("11th");
        assert NumberUtils.convertToOrdinal(12).equals("12th");
        assert NumberUtils.convertToOrdinal(13).equals("13th");
        assert NumberUtils.convertToOrdinal(14).equals("14th");
        assert NumberUtils.convertToOrdinal(15).equals("15th");
        assert NumberUtils.convertToOrdinal(16).equals("16th");
        assert NumberUtils.convertToOrdinal(17).equals("17th");
        assert NumberUtils.convertToOrdinal(18).equals("18th");
        assert NumberUtils.convertToOrdinal(19).equals("19th");
        assert NumberUtils.convertToOrdinal(20).equals("20th");
    }
}
